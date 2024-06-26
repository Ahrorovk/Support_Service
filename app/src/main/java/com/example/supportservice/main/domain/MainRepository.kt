package com.example.supportservice.main.domain

import com.example.supportservice.main.domain.main.models.application.AddApplicationBody
import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.application.ApplicationByIdReceiveRemote
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationBody
import com.example.supportservice.main.domain.main.models.application.ApplicationResponseRemote
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote
import retrofit2.http.Body
import retrofit2.http.Header

interface MainRepository {
    suspend fun getApplicationsByEmail(
        token: String
    ): ApplicationsResponseRemote


    suspend fun getAllApplications(
        token: String
    ): ApplicationsResponseRemote

    suspend fun getApplicationById(
        token: String,
        idBody: ApplicationByIdReceiveRemote
    ): ApplicationsResponseRemote


    suspend fun deleteApplicationById(
        token: String,
        updateApplicationBody: ApplicationByIdReceiveRemote
    ): ApplicationResponseRemote

    suspend fun addApplication(
        token: String,
        applicationBody: AddApplicationBody
    ): AddApplicationResponse

    suspend fun updateApplication(
        token: String,
        updateApplicationBody: UpdateApplicationBody
    ): ApplicationResponseRemote

    suspend fun getAllStatuses(): StatusResponseRemote
}