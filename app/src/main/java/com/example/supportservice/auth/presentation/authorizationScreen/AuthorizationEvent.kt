package com.example.supportservice.auth.presentation.authorizationScreen


sealed class AuthorizationEvent {
    data class OnLoginChange(val login: String) : AuthorizationEvent()
    data class OnPasswordChange(val password: String) : AuthorizationEvent()
//    data class OnAuthorizationStateChange(val state: AuthorizationRespState) : AuthorizationEvent()
    object Authorization : AuthorizationEvent()
    object GoToMainScreen : AuthorizationEvent()
    object GoToSignUp : AuthorizationEvent()
}
