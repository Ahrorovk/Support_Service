package com.example.supportservice.main.domain.main.states.application

import com.example.supportservice.main.domain.main.models.application.ApplicationResponseRemote

data class UpdateApplicationResponseState(
    val isLoading: Boolean = false,
    val response: ApplicationResponseRemote? = null,
    val error: String = ""
)
