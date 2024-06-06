package com.example.supportservice.main.domain

import com.example.supportservice.main.domain.main.models.ApplicationsResponseRemote

interface MainRepository {
    suspend fun getApplicationsByEmail(
        token: String
    ): ApplicationsResponseRemote
}