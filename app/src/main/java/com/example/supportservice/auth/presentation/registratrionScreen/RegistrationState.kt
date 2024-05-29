package com.example.supportservice.auth.presentation.registratrionScreen

import com.example.supportservice.core.domain.states.RoleState
import com.example.supportservice.core.util.getRoles

data class RegistrationState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
//    val registrationRespState: RegistrationRespState = RegistrationRespState(),
    val selectedRole: RoleState = RoleState(),
    val phone: String = "",
    val rolesState: List<RoleState> = getRoles()
)
