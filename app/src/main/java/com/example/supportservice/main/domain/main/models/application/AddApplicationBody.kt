package com.example.supportservice.main.domain.main.models.application

data class AddApplicationBody(
    val title: String,
    val email: String,
    val phone_number: String,
    val description: String
)
