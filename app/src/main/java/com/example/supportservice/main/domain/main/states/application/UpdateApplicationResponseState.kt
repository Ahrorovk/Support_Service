package com.example.supportservice.main.domain.main.states.application

import com.example.supportservice.main.domain.main.models.application.UpdateApplicationResponseRemote

data class UpdateApplicationResponseState(
    val isLoading: Boolean = false,
    val response: UpdateApplicationResponseRemote? = null,
    val error: String = ""
)
