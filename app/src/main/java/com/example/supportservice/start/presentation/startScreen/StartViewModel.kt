package com.example.supportservice.start.presentation.startScreen

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
class StartViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _state = MutableStateFlow(StartState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        StartState()
    )

    init {
        dataStoreManager.getAccessToken.onEach { value ->
            _state.update {
                it.copy(
                    token = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: StartEvent) {
        when (event) {
            else -> Unit
        }
    }
}