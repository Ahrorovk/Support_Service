package com.example.supportservice.main.domain.main.models.application

data class UpdateApplicationBody(
    val status: String,
    val comment: String,
    val id: Int
)
