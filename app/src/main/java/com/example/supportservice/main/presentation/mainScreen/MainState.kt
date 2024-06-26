package com.example.supportservice.main.presentation.mainScreen

import com.example.supportservice.main.domain.main.models.application.Application
import com.example.supportservice.main.domain.main.states.application.GetApplicationsResponseState
import com.example.supportservice.main.domain.main.states.application.UpdateApplicationResponseState
import com.example.supportservice.main.domain.main.states.status.GetAllStatusesResponseState
import com.example.supportservice.user.domain.states.UserResponseState

data class MainState(
    val searchState: String = "",
    val email: String = "",
    val appId: Int = 0,
    val phoneNumber: String = "",
    val applicationsRespState: GetApplicationsResponseState = GetApplicationsResponseState(),
    val refreshToken: String = "",
    val selectedRoleId: Int = 0,
    val deleteByIdApplicationState: Int = 0,
    val isAdded: Boolean = false,
    val deleteApplicationDialogState: Boolean = false,
    val sortedApplications: List<Application> = emptyList(),
    val selectedStatus: String = "",
    val allStatusesResponseState: GetAllStatusesResponseState = GetAllStatusesResponseState(),
    val userResponseState: UserResponseState = UserResponseState(),
    val accessToken: String = "",
    val statusState: String = "",
    val commentState: String = "",
    val updateResponseState: UpdateApplicationResponseState = UpdateApplicationResponseState()
)
