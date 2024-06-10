package com.example.supportservice.main.domain.main.states.application

import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse

data class AddApplicationResponseState(
    val isLoading: Boolean = false,
    val response: AddApplicationResponse? = null,
    val error: String = ""
)