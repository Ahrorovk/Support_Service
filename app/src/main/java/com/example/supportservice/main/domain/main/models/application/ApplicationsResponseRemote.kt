package com.example.supportservice.main.domain.main.models.application

import com.example.supportservice.main.domain.main.models.application.Application

data class ApplicationsResponseRemote(
    val applications: List<Application>,
    val status: String,
    val statusCode: Int
)