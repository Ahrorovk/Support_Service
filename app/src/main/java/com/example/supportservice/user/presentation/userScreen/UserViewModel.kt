package com.example.supportservice.user.presentation.userScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportservice.core.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        UserState()
    )

    fun onEvent(event: UserEvent) {
        when (event) {
            else -> Unit
        }
    }
}