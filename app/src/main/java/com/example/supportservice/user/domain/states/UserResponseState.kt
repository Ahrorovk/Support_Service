package com.example.supportservice.user.domain.states

import com.example.supportservice.user.domain.models.UserResponseRemote

data class UserResponseState(
    val isLoading: Boolean = false,
    val response: UserResponseRemote? = null,
    val error: String = ""
)