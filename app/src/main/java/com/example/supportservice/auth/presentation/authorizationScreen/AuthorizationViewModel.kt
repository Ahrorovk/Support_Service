package com.example.supportservice.auth.presentation.authorizationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.auth.domain.auth.models.LoginReceiveRemote
import com.example.supportservice.auth.domain.auth.models.LoginResponseRemote
import com.example.supportservice.auth.domain.auth.states.LoginResponseState
import com.example.supportservice.auth.domain.auth.use_cases.AuthorizationUseCase
import com.example.supportservice.core.data.local.DataStoreManager
import com.example.supportservice.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorizationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        AuthorizationState()
    )

    init {
        dataStoreManager.getFcmTokenKey.onEach { value ->
            Log.e("TAG","FCM_TOKEN->\n -> $value")
            _state.update {
                it.copy(
                    fcmTokenState = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.login
                    )
                }
            }

            is AuthorizationEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is AuthorizationEvent.OnAuthorizationStateChange -> {
                _state.update {
                    it.copy(
                        authorizationRespState = event.loginResponseState
                    )
                }
            }

            AuthorizationEvent.Authorization -> {
                authorization(
                    _state.value.email,
                    _state.value.password,
                    _state.value.fcmTokenState
                )
            }

            else -> Unit
        }
    }

    fun authorization(email: String, password: String, fcmToken: String) {
        authorizationUseCase.invoke(
            LoginReceiveRemote(
                email,
                password,
                fcmToken
            )
        ).onEach { result: Resource<LoginResponseRemote> ->
            when (result) {
                is Resource.Success -> {
                    val response: LoginResponseRemote? = result.data
                    onEvent(
                        AuthorizationEvent.OnAuthorizationStateChange(
                            LoginResponseState(
                                response = response
                            )
                        )
                    )
                    viewModelScope.launch {
                        dataStoreManager.updateAccessToken(response?.token ?: "")
                    }
                    Log.e("TAG", "AuthorizationResponse->\n ${_state.value.authorizationRespState}")
                }

                is Resource.Error -> {
                    Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                    onEvent(
                        AuthorizationEvent.OnAuthorizationStateChange(
                            LoginResponseState(
                                error = "${result.message}"
                            )
                        )
                    )
                }

                is Resource.Loading -> {
                    onEvent(
                        AuthorizationEvent.OnAuthorizationStateChange(
                            LoginResponseState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}