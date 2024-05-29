package com.example.supportservice.auth.presentation.authorizationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.core.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
//    private val authorizationUseCase: AuthorizationUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorizationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        AuthorizationState()
    )

    init {
        dataStoreManager.getLogin.onEach { value ->
            _state.update {
                it.copy(
                    savedUsername = value
                )
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getPassword.onEach { value ->
            _state.update {
                it.copy(
                    savedPassword = value
                )
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.OnLoginChange -> {
                _state.update {
                    it.copy(
                        username = event.login
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

//            is AuthorizationEvent.OnAuthorizationStateChange -> {
//                _state.update {
//                    it.copy(
//                        authorizationRespState = event.state
//                    )
//                }
//            }

            AuthorizationEvent.Authorization -> {


//                authorization(
//                    _state.value.username,
//                    _state.value.password
//                )
            }

            else -> Unit
        }
    }

    fun authorization(username: String, password: String) {
        /*authorizationUseCase.invoke(
            AuthorizationBody(
                username,
                password
            )
        ).onEach { result: Resource<AuthorizationResp> ->
            when (result) {
                is Resource.Success -> {
                    val response: AuthorizationResp? = result.data
                    onEvent(
                        AuthorizationEvent.OnAuthorizationStateChange(
                            AuthorizationRespState(
                                response = response
                            )
                        )
                    )
                    viewModelScope.launch {
                        if (response != null) {
                            dataStoreManager.updateRefreshToken(response.refresh)
                            dataStoreManager.updateAccessToken(response.access)
                        }
                    }
                    Log.e("TAG", "AuthorizationResponse->\n ${_state.value.authorizationRespState}")
                }

                is Resource.Error -> {
                    Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                    onEvent(
                        AuthorizationEvent.OnAuthorizationStateChange(
                            AuthorizationRespState(
                                error = "${result.message}"
                            )
                        )
                    )
                }

                is Resource.Loading -> {
                    onEvent(
                        AuthorizationEvent.OnAuthorizationStateChange(
                            AuthorizationRespState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)*/
    }
}