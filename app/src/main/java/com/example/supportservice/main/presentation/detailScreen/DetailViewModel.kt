package com.example.supportservice.main.presentation.detailScreen

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
import com.example.supportservice.main.domain.main.use_cases.application.UpdateApplicationUseCase
import com.example.supportservice.main.domain.main.use_cases.status.GetAllStatusesUseCase
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
class DetailViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getAllApplicationsUseCase: GetAllApplicationsUseCase,
    private val updateApplicationUseCase: UpdateApplicationUseCase,
    private val getAllStatusesUseCase: GetAllStatusesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        DetailState()
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

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnIdChange -> {
                _state.update {
                    it.copy(
                        id = event.id
                    )
                }
            }

            is DetailEvent.OnSelectedStatusChange -> {
                _state.update {
                    it.copy(
                        selectedStatus = event.status
                    )
                }
            }

            DetailEvent.GetAllStatuses -> {
                getAllStatuses()
            }

            is DetailEvent.OnCommentChange -> {
                _state.update {
                    it.copy(
                        comment = event.comment
                    )
                }
            }

            is DetailEvent.GetApplicationById -> {
                getApplicationById(event.id)
            }

            DetailEvent.UpdateApplication -> {
                updateApplication()
            }

            else -> {}
        }
    }

    private fun updateApplication() {
        updateApplicationUseCase.invoke(
            _state.value.accessToken,
            UpdateApplicationBody(
                _state.value.selectedStatus,
                _state.value.comment,
                _state.value.id
            )
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

    fun getApplicationById(id: Int) {
        getAllApplicationsUseCase.invoke(
            _state.value.accessToken
        ).onEach { result: Resource<ApplicationsResponseRemote> ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            applicationsResponseState = GetApplicationsResponseState(
                                error = result.message.toString()
                            )
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            applicationsResponseState = GetApplicationsResponseState(
                                isLoading = true
                            )
                        )
                    }
                }

                is Resource.Success -> {
                    result.data?.let { response ->
                        response.applications.forEach {
                            if (it.id == _state.value.id) {
                                val resp = ApplicationsResponseRemote(
                                    listOf(it),
                                    response.status,
                                    response.statusCode
                                )
                                _state.update {
                                    it.copy(
                                        applicationsResponseState = GetApplicationsResponseState(
                                            response = resp
                                        ),
                                        selectedStatus = resp.applications[0].status ?: "",
                                        comment = resp.applications[0].comment ?: ""
                                    )
                                }
                            }
                        }
                    }
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
                        _state.update {
                            it.copy(allStatusesResponseState = GetAllStatusesResponseState(response = res))
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}