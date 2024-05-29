package com.example.supportservice.auth.presentation.authorizationScreen

data class AuthorizationState(
    val username: String = "",
    val password: String = "",
    val savedUsername: String = "",
    val savedPassword:String = ""
//    val authorizationRespState: AuthorizationRespState = AuthorizationRespState()
)
