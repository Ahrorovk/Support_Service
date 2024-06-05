package com.example.supportservice.auth.domain.registr.models

data class RegistrationReceiveRemote(
    val password: String,
    val email: String,
    val name: String,
    val phone_number: String,
    val role: String
)
