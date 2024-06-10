package com.example.supportservice.main.domain

import com.example.supportservice.main.domain.main.models.application.AddApplicationBody
import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.application.GetApplicationByIdReceiveRemote
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationBody
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationResponseRemote
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote

interface MainRepository {
    suspend fun getApplicationsByEmail(
        token: String
    ): ApplicationsResponseRemote


    suspend fun getAllApplications(
        token: String
    ): ApplicationsResponseRemote

    suspend fun getApplicationById(
        token: String,
        idBody: GetApplicationByIdReceiveRemote
    ): ApplicationsResponseRemote

    suspend fun addApplication(
        token: String,
        applicationBody: AddApplicationBody
    ): AddApplicationResponse

    suspend fun updateApplication(
        token: String,
        updateApplicationBody: UpdateApplicationBody
    ): UpdateApplicationResponseRemote

    suspend fun getAllStatuses(): StatusResponseRemote
}