package com.example.supportservice.main.domain.main.states.application

import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote

class GetApplicationsResponseState(
    val isLoading: Boolean = false,
    val response: ApplicationsResponseRemote? = null,
    val error: String = ""
)