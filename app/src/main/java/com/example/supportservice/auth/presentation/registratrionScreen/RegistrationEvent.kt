package com.example.supportservice.auth.presentation.registratrionScreen

import com.example.supportservice.auth.domain.registr.states.RegistrationResponseState
import com.example.supportservice.core.domain.states.RoleState

sealed class RegistrationEvent {
    data class OnRegistrationRespStateChange(val registrationResponseState: RegistrationResponseState) :
        RegistrationEvent()

    data class OnEmailChange(val email: String) : RegistrationEvent()
    data class OnUsernameChange(val username: String) : RegistrationEvent()
    data class OnRoleChange(val role: RoleState) : RegistrationEvent()
    data class OnPhoneChange(val phone: String) : RegistrationEvent()
    data class OnPasswordChange(val password: String) : RegistrationEvent()

    data class OnPasswordConfirmChange(val passwordConfirm: String) : RegistrationEvent()

    //    data class OnRegistrationRespStateChange(val state: RegistrationRespState): RegistrationEvent()
    object Registration : RegistrationEvent()
    object GoToAuthScreen : RegistrationEvent()
    object GoToSignUp : RegistrationEvent()
}
