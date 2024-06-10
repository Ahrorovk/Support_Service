package com.example.supportservice.user.presentation.userScreen

import com.example.supportservice.user.domain.states.UserResponseState

data class UserState(
    val userId: Int = 0,
    val userResponseState: UserResponseState = UserResponseState(),
    val accessToken: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val roleId: Int = 0
)
