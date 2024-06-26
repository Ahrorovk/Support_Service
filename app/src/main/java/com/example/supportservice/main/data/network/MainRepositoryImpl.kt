package com.example.supportservice.main.data.network

import com.example.supportservice.main.data.network.remote.MainApi
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.application.AddApplicationBody
import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.application.ApplicationByIdReceiveRemote
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationBody
import com.example.supportservice.main.domain.main.models.application.ApplicationResponseRemote
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote

class MainRepositoryImpl(
    private val api: MainApi
) : MainRepository {
    override suspend fun getApplicationsByEmail(token: String): ApplicationsResponseRemote =
        api.getApplicationsByEmail(token)

    override suspend fun getApplicationById(
        token: String,
        idBody: ApplicationByIdReceiveRemote
    ): ApplicationsResponseRemote =
        api.getApplicationById(token, idBody)

    override suspend fun deleteApplicationById(
        token: String,
        deleteApplicationBody: ApplicationByIdReceiveRemote
    ): ApplicationResponseRemote = api.deleteApplicationById(token, deleteApplicationBody)

    override suspend fun getAllApplications(token: String): ApplicationsResponseRemote =
        api.getAllApplications(token)

    override suspend fun addApplication(
        token: String,
        applicationBody: AddApplicationBody
    ): AddApplicationResponse =
        api.addApplication(token, applicationBody)

    override suspend fun updateApplication(
        token: String,
        updateApplicationBody: UpdateApplicationBody
    ): ApplicationResponseRemote = api.updateApplication(token, updateApplicationBody)

    override suspend fun getAllStatuses(): StatusResponseRemote = api.getAllStatuses()
}