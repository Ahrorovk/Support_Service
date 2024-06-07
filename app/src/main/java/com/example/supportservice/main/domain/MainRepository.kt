package com.example.supportservice.main.domain

import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote

interface MainRepository {
    suspend fun getApplicationsByEmail(
        token: String
    ): ApplicationsResponseRemote

    suspend fun getAllApplications(
        token: String
    ): ApplicationsResponseRemote

    suspend fun getAllStatuses(): StatusResponseRemote
}