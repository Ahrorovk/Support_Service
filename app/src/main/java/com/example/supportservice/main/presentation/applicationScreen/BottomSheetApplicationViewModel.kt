package com.example.supportservice.main.presentation.applicationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.core.data.local.DataStoreManager
import com.example.supportservice.core.util.BottomSheetApplyOneTimeEvent
import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.main.models.application.AddApplicationBody
import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse
import com.example.supportservice.main.domain.main.states.application.AddApplicationResponseState
import com.example.supportservice.main.domain.main.use_cases.application.AddApplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class BottomSheetApplicationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val addApplicationUseCase: AddApplicationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(BottomSheetApplicationState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        BottomSheetApplicationState()
    )
    private val channel = Channel<BottomSheetApplyOneTimeEvent>()
    val flow = channel.receiveAsFlow()

    init {
        dataStoreManager.getIsAdded.onEach { value ->
            _state.update {
                it.copy(
                    isAdded = true
                )
            }

        }.launchIn(viewModelScope)
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    accessToken = value
                )
            }
        }.launchIn(viewModelScope)
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    accessToken = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: BottomSheetApplicationEvent) {
        when (event) {
            is BottomSheetApplicationEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = event.state
                    )
                }
            }

            is BottomSheetApplicationEvent.OnPhoneNumberChange -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.number
                    )
                }
            }

            is BottomSheetApplicationEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = event.state
                    )
                }
            }

            is BottomSheetApplicationEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.state
                    )
                }
            }

            BottomSheetApplicationEvent.Apply -> {
                viewModelScope.launch {
                    addApplication()
                    channel.send(BottomSheetApplyOneTimeEvent.CloseBottomSheet)
                }
            }

            else -> Unit
        }
    }

    private fun addApplication() {
        addApplicationUseCase.invoke(
            token = _state.value.accessToken,
            AddApplicationBody(
                _state.value.title,
                _state.value.email,
                _state.value.phoneNumber,
                _state.value.description
            )
        ).onEach { result: Resource<AddApplicationResponse> ->
            when (result) {
                is Resource.Success -> {
                    val response: AddApplicationResponse? = result.data
                    _state.update {
                        it.copy(
                            applicationResponseState = AddApplicationResponseState(response = response)
                        )
                    }
                    viewModelScope.launch {
                        dataStoreManager.updateIsAdded(true)
                    }
                    Log.e(
                        "TAG",
                        "AddApplicationResponse->\n ${_state.value}"
                    )
                }

                is Resource.Error -> {
                    Log.e("TAG", "AddApplicationResponseError->\n ${result.message}")
                    _state.update {
                        it.copy(
                            applicationResponseState = AddApplicationResponseState(error = result.message.toString())
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            applicationResponseState = AddApplicationResponseState(isLoading = true)
                        )
                    }
                }

            }
        }.launchIn(viewModelScope)
    }
}