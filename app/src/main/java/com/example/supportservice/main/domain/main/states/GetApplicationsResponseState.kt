package com.example.supportservice.main.domain.main.states

import com.example.supportservice.main.domain.main.models.ApplicationsResponseRemote

class GetApplicationsResponseState(
    val isLoading: Boolean = false,
    val response: ApplicationsResponseRemote? = null,
    val error: String = ""
)