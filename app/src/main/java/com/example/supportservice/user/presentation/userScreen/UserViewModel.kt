package com.example.supportservice.user.presentation.userScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.core.data.local.DataStoreManager
import com.example.supportservice.core.util.Resource
import com.example.supportservice.user.domain.models.UserResponseRemote
import com.example.supportservice.user.domain.states.UserResponseState
import com.example.supportservice.user.domain.use_cases.GetUserUseCase
import com.example.supportservice.user.domain.use_cases.UpdateUserUseCase
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
class UserViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        UserState()
    )

    init {
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    accessToken = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: UserEvent) {
        when (event) {
            UserEvent.GetUser -> {
                getUser()
            }

            is UserEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is UserEvent.OnPhoneNumberChange -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumberChange
                    )
                }
            }

            UserEvent.UpdateUser -> {
                updateUser()
            }

            else -> Unit
        }
    }

    private fun updateUser() {
        updateUserUseCase.invoke(
            _state.value.accessToken, UserResponseRemote(
                _state.value.name,
                _state.value.userResponseState.response?.email ?: "",
                _state.value.phoneNumber,
                _state.value.userResponseState.response?.password ?: "",
                _state.value.roleId
            )
        ).onEach { result: Resource<String> ->
            when (result) {
                is Resource.Error -> {
                    result.message?.let { mes ->
                        _state.update {
                            it.copy(
                                userResponseState = UserResponseState(error = mes)
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            userResponseState = UserResponseState(isLoading = true)
                        )
                    }
                }

                is Resource.Success -> {
                    val response = result.data
                    _state.update {
                        it.copy(
                            userResponseState = UserResponseState(error = response ?: "")
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUser() {
        Log.e("TOKEN", "token--->${_state.value.accessToken}")
        getUserUseCase.invoke(_state.value.accessToken)
            .onEach { result: Resource<UserResponseRemote> ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let { mes ->
                            _state.update {
                                it.copy(
                                    userResponseState = UserResponseState(error = mes)
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                userResponseState = UserResponseState(isLoading = true)
                            )
                        }
                    }

                    is Resource.Success -> {
                        val response = result.data
                        _state.update {
                            it.copy(
                                userResponseState = UserResponseState(response = response)
                            )
                        }
                        viewModelScope.launch {
                            dataStoreManager.updateRoleId(response?.role_id ?: 0)
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
}