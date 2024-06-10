package com.example.supportservice.auth.presentation.authorizationScreen

import com.example.supportservice.auth.domain.auth.states.LoginResponseState


sealed class AuthorizationEvent {
    data class OnEmailChange(val login: String) : AuthorizationEvent()
    data class OnPasswordChange(val password: String) : AuthorizationEvent()
    data class OnAuthorizationStateChange(val loginResponseState: LoginResponseState): AuthorizationEvent()
    object Authorization : AuthorizationEvent()
    object GoToMainScreen : AuthorizationEvent()
    object GoToSignUp : AuthorizationEvent()
}
