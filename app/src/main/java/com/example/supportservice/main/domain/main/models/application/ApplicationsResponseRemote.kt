package com.example.supportservice.main.domain.main.models.application

data class ApplicationsResponseRemote(
    val applications: List<Application>,
    val status: String,
    val statusCode: Int
)