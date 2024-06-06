package com.example.supportservice.auth.presentation.authorizationScreen

import com.example.supportservice.auth.domain.auth.states.LoginResponseState

data class AuthorizationState(
    val username: String = "",
    val password: String = "",
    val authorizationRespState: LoginResponseState = LoginResponseState()
)
