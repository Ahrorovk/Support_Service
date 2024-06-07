package com.example.supportservice.main.presentation.mainScreen

import com.example.supportservice.main.domain.main.states.application.GetApplicationsResponseState
import com.example.supportservice.main.domain.main.states.status.GetAllStatusesResponseState
import com.example.supportservice.user.domain.states.UserResponseState

data class MainState(
    val searchState: String = "",
    val applicationsRespState: GetApplicationsResponseState = GetApplicationsResponseState(),
    val refreshToken: String = "",
    val accessToken: String = "",
    val selectedRoleId: Int = 0,
    val selectedStatus: String = "",
    val allStatusesResponseState: GetAllStatusesResponseState = GetAllStatusesResponseState(),
    val userResponseState: UserResponseState = UserResponseState()
)
