package com.example.supportservice.auth.domain.auth.states

import com.example.supportservice.auth.domain.auth.models.LoginResponseRemote

data class LoginResponseState(
    val isLoading: Boolean = false,
    val response: LoginResponseRemote? = null,
    val error: String = ""
)
