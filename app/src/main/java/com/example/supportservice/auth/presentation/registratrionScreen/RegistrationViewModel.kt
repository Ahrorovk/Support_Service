package com.example.supportservice.auth.presentation.registratrionScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.auth.domain.registr.states.RegistrationResponseState
import com.example.supportservice.auth.domain.registr.models.RegistrationReceiveRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationResponseRemote
import com.example.supportservice.auth.domain.registr.use_cases.RegistrationUseCase
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
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        RegistrationState()
    )

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is RegistrationEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is RegistrationEvent.OnPasswordConfirmChange -> {
                _state.update {
                    it.copy(
                        passwordConfirm = event.passwordConfirm
                    )
                }
            }

            is RegistrationEvent.OnUsernameChange -> {
                _state.update {
                    it.copy(
                        username = event.username
                    )
                }
            }

            is RegistrationEvent.OnPhoneChange -> {
                _state.update {
                    it.copy(
                        phone = event.phone
                    )
                }
            }

            RegistrationEvent.Registration -> {
                viewModelScope.launch(Dispatchers.IO) {
                }
                registration()
            }

            is RegistrationEvent.OnRegistrationRespStateChange -> {
                _state.update {
                    it.copy(
                        registrationRespState = event.registrationResponseState
                    )
                }
            }

            is RegistrationEvent.OnRoleChange -> {
                _state.update {
                    it.copy(
                        selectedRole = event.role
                    )
                }
            }

            else -> Unit
        }
    }

    private fun registration() {

        registrationUseCase.invoke(
            RegistrationReceiveRemote(
                name = _state.value.username,
                email = _state.value.email,
                phone_number = _state.value.phone,
                password = _state.value.password,
                role_id = _state.value.selectedRole.roleId
            )
        ).onEach { result: Resource<RegistrationResponseRemote> ->
            when (result) {
                is Resource.Success -> {
                    val response: RegistrationResponseRemote? = result.data
                    onEvent(
                        RegistrationEvent.OnRegistrationRespStateChange(
                            RegistrationResponseState(
                                response = response
                            )
                        )
                    )
                    viewModelScope.launch {
                        if (response != null) {
                            dataStoreManager.updateAccessToken(response.token)
                            dataStoreManager.updateRoleId(_state.value.selectedRole.roleId)
                        }
                    }
                    Log.e("TAG", "AuthorizationResponse->\n ${_state.value.registrationRespState}")
                }

                is Resource.Error -> {
                    Log.e("TAG", "AuthorizationResponseError->\n ${result.message}")
                    onEvent(
                        RegistrationEvent.OnRegistrationRespStateChange(
                            RegistrationResponseState(
                                error = "${result.message}"
                            )
                        )
                    )
                }

                is Resource.Loading -> {
                    onEvent(
                        RegistrationEvent.OnRegistrationRespStateChange(
                            RegistrationResponseState(
                                isLoading = true
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}