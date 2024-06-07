package com.example.supportservice.main.domain.main.states.status

import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote

data class GetAllStatusesResponseState(
    val isLoading: Boolean = false,
    val response: StatusResponseRemote? = null,
    val error: String = ""
)
