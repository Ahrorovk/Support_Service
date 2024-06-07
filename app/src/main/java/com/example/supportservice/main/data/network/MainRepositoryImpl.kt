package com.example.supportservice.main.data.network

import com.example.supportservice.main.data.network.remote.MainApi
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote

class MainRepositoryImpl(
    private val api: MainApi
) : MainRepository {
    override suspend fun getApplicationsByEmail(token: String): ApplicationsResponseRemote =
        api.getApplicationsByEmail(token)

    override suspend fun getAllApplications(token: String): ApplicationsResponseRemote =
        api.getAllApplications(token)

    override suspend fun getAllStatuses(): StatusResponseRemote = api.getAllStatuses()
}