package com.example.supportservice.main.domain.main.models.status

data class StatusResponseRemote(
    val allStatuses: List<Status>,
    val statusCode: Int,
    val status: String
)