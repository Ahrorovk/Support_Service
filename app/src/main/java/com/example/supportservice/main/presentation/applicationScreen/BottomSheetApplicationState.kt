package com.example.supportservice.main.presentation.applicationScreen

import com.example.supportservice.main.domain.main.states.application.AddApplicationResponseState

data class BottomSheetApplicationState(
    val description: String = "",
    val isAdded: Boolean = false,
    val title: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val accessToken: String = "",
    val statusOfSendResume: Boolean = false,
    val applicationResponseState: AddApplicationResponseState = AddApplicationResponseState()
)
