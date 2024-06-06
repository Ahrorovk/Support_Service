package com.ahrorovkspace.afkorhackathon.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.core.data.local.DataStoreManager
import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.main.models.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.states.GetApplicationsResponseState
import com.example.supportservice.main.domain.main.use_cases.GetApplicationsByEmailUseCase
import com.example.supportservice.main.presentation.mainScreen.MainEvent
import com.example.supportservice.main.presentation.mainScreen.MainState
import com.example.supportservice.user.domain.models.UserResponseRemote
import com.example.supportservice.user.domain.states.UserResponseState
import com.example.supportservice.user.domain.use_cases.GetUserUseCase
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
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getApplicationsByEmailUseCase: GetApplicationsByEmailUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        MainState()
    )

    init {
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    accessToken = value
                )
            }
        }.launchIn(viewModelScope)

        dataStoreManager.getRoleId.onEach { value ->
            _state.update {
                it.copy(
                    selectedRoleId = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnSearchProjectChange -> {
                _state.update {
                    it.copy(
                        searchState = event.state
                    )
                }
            }


            is MainEvent.GetApplicationsByEmail -> {
                getApplicationsByEmail()
            }

            MainEvent.GetUser -> {
                getUser()
            }

            is MainEvent.OnGetApplicationsResponseStateChange -> {
                _state.update {
                    it.copy(
                        applicationsRespState = event.state
                    )
                }
            }

            MainEvent.Clean -> {
                /*viewModelScope.launch {
                    dataStoreManager.updateAccessToken("")
                    dataStoreManager.updateRefreshToken("")
                }*/
            }

            else -> {}
        }
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
                        viewModelScope.launch {
                            dataStoreManager.updateRoleId(response?.role_id ?: 0)
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getApplicationsByEmail() {
        Log.e("TOKEN", "token->${_state.value.accessToken}")
        getApplicationsByEmailUseCase.invoke(
            token = _state.value.accessToken
        ).onEach { result: Resource<ApplicationsResponseRemote> ->
            when (result) {
                is Resource.Success -> {
                    val response: ApplicationsResponseRemote? = result.data
                    onEvent(
                        MainEvent.OnGetApplicationsResponseStateChange(
                            GetApplicationsResponseState(
                                response = response
                            )
                        )
                    )
                    Log.e(
                        "TAG",
                        "GetApplicationsResponse->\n ${_state.value.applicationsRespState.response}"
                    )
                }

                is Resource.Error -> {
                    Log.e("TAG", "GetApplicationsResponseError->\n ${result.message}")
                    onEvent(
                        MainEvent.OnGetApplicationsResponseStateChange(
                            GetApplicationsResponseState(
                                error = "${result.message}"
                            )
                        )
                    )
                }

                is Resource.Loading -> {
                    onEvent(
                        MainEvent.OnGetApplicationsResponseStateChange(
                            GetApplicationsResponseState(
                                isLoading = true
                            )
                        )
                    )
                }

            }
        }.launchIn(viewModelScope)
    }

}