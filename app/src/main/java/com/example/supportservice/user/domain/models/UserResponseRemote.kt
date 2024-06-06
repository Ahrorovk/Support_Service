package com.example.supportservice.user.domain.models

data class UserResponseRemote (
    val name: String,
    val email: String,
    val phone_number: String,
    val password: String,
    val role_id: Int
)