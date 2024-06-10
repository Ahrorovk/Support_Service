package com.example.supportservice.main.presentation.detailScreen

import com.example.supportservice.main.domain.main.states.application.GetApplicationsResponseState
import com.example.supportservice.main.domain.main.states.application.UpdateApplicationResponseState
import com.example.supportservice.main.domain.main.states.status.GetAllStatusesResponseState

data class DetailState(
    val id: Int = 0,
    val selectedRoleId: Int = 0,
    val comment: String = "",
    val selectedStatus: String = "",
    val allStatusesResponseState: GetAllStatusesResponseState = GetAllStatusesResponseState(),
    val accessToken: String = "",
    val applicationsResponseState: GetApplicationsResponseState = GetApplicationsResponseState(),
    val updateResponseState: UpdateApplicationResponseState = UpdateApplicationResponseState()
)
