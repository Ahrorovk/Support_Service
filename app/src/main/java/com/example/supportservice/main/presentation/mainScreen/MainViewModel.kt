package com.example.supportservice.main.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.core.data.local.DataStoreManager
import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationBody
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote
import com.example.supportservice.main.domain.main.states.application.GetApplicationsResponseState
import com.example.supportservice.main.domain.main.states.application.UpdateApplicationResponseState
import com.example.supportservice.main.domain.main.states.status.GetAllStatusesResponseState
import com.example.supportservice.main.domain.main.use_cases.application.GetAllApplicationsUseCase
import com.example.supportservice.main.domain.main.use_cases.application.GetApplicationsByEmailUseCase
import com.example.supportservice.main.domain.main.use_cases.application.UpdateApplicationUseCase
import com.example.supportservice.main.domain.main.use_cases.status.GetAllStatusesUseCase
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
    private val getAllApplications: GetAllApplicationsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllStatusesUseCase: GetAllStatusesUseCase,
    private val updateApplicationUseCase: UpdateApplicationUseCase
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

        dataStoreManager.getIsAdded.onEach { value ->
            _state.update {
                it.copy(
                    isAdded = value
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

            is MainEvent.OnIsAddedChange -> {
                viewModelScope.launch {
                    dataStoreManager.updateIsAdded(event.state)
                }
            }

            is MainEvent.OnSelectedStatusChange -> {
                _state.update {
                    it.copy(
                        selectedStatus = event.status
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

            MainEvent.GetAllStatuses -> {
                getAllStatuses()
            }

            is MainEvent.OnSortedApplicationsChange -> {
                _state.update {
                    it.copy(
                        sortedApplications = event.sortedApplicationsChange
                    )
                }
            }

            is MainEvent.UpdateStatus -> {
                _state.update {
                    it.copy(
                        appId = event.id
                    )
                }
                if (_state.value.selectedRoleId == 2)
                    updateApplication(event.id)
            }

            is MainEvent.OnCommentChange -> {
                _state.update {
                    it.copy(
                        commentState = event.state
                    )
                }
            }

            MainEvent.GetAllApplications -> {
                getAllApplications()
            }

            MainEvent.Clean -> {
                viewModelScope.launch {
                    dataStoreManager.updateAccessToken("")
                }
            }

            else -> {}
        }
    }

    private fun updateApplication(id: Int) {
        updateApplicationUseCase.invoke(
            _state.value.accessToken,
            UpdateApplicationBody("Открытый", _state.value.commentState, id)
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(updateResponseState = UpdateApplicationResponseState(error = result.message.toString()))
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(updateResponseState = UpdateApplicationResponseState(isLoading = true))
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(updateResponseState = UpdateApplicationResponseState(response = result.data))
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

                        response?.let { res ->
                            viewModelScope.launch {
                                dataStoreManager.updateRoleId(res.role_id)
                            }
                            _state.update {
                                it.copy(
                                    email = res.email,
                                    phoneNumber = res.phone_number
                                )
                            }
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
                    _state.update {
                        it.copy(
                            sortedApplications = response?.applications ?: emptyList()
                        )
                    }
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

    fun getAllApplications() {
        getAllApplications.invoke(
            token = _state.value.accessToken
        ).onEach { result: Resource<ApplicationsResponseRemote> ->
            when (result) {
                is Resource.Success -> {
                    val response: ApplicationsResponseRemote? = result.data
                    _state.update {
                        it.copy(
                            sortedApplications = response?.applications ?: emptyList()
                        )
                    }
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

    fun getAllStatuses() {
        getAllStatusesUseCase.invoke().onEach { result: Resource<StatusResponseRemote> ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            allStatusesResponseState = GetAllStatusesResponseState(error = result.message.toString())
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            allStatusesResponseState = GetAllStatusesResponseState(true)
                        )
                    }
                }

                is Resource.Success -> {
                    val response = result.data
                    response?.let { res ->
                        onEvent(MainEvent.OnSelectedStatusChange("Все"))
                        _state.update {
                            it.copy(allStatusesResponseState = GetAllStatusesResponseState(response = res))
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}