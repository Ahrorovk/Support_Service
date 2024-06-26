package com.example.supportservice.main.domain.main.models.application

data class Application(
    val comment: String?,
    val description: String,
    val email: String,
    val id: Int,
    val phone_number: String,
    val status: String?,
    val title: String
)