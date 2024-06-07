package com.example.supportservice.main.data.network.remote

import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApi {

    @GET("application/get/byEmail")
    suspend fun getApplicationsByEmail(
        @Header("Bearer-Authorization") token: String
    ): ApplicationsResponseRemote

    @GET("application/get")
    suspend fun getAllApplications(
        @Header("Bearer-Authorization") token: String
    ): ApplicationsResponseRemote

    @GET("status/get")
    suspend fun getAllStatuses(): StatusResponseRemote
}