package com.example.supportservice.auth.domain.registr.states

import com.example.supportservice.auth.domain.registr.models.RegistrationResponseRemote

data class RegistrationResponseState(
    val isLoading: Boolean = false,
    val response: RegistrationResponseRemote? = null,
    val error: String = ""
)