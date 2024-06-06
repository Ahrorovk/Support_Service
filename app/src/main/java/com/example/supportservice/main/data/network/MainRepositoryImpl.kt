package com.example.supportservice.main.data.network

import com.example.supportservice.main.data.network.remote.MainApi
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.ApplicationsResponseRemote

class MainRepositoryImpl(
    private val api: MainApi
) : MainRepository {
    override suspend fun getApplicationsByEmail(token: String): ApplicationsResponseRemote =
        api.getApplicationsByEmail(token)

}