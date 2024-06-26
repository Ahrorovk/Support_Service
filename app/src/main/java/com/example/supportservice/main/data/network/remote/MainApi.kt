package com.example.supportservice.main.data.network.remote

import com.example.supportservice.main.domain.main.models.application.AddApplicationBody
import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.application.ApplicationByIdReceiveRemote
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationBody
import com.example.supportservice.main.domain.main.models.application.ApplicationResponseRemote
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MainApi {

    @GET("application/get/byEmail")
    suspend fun getApplicationsByEmail(
        @Header("Bearer-Authorization") token: String
    ): ApplicationsResponseRemote

    @GET("application/get")
    suspend fun getAllApplications(
        @Header("Bearer-Authorization") token: String,
    ): ApplicationsResponseRemote

    @GET("application/get/byId")
    suspend fun getApplicationById(
        @Header("Bearer-Authorization") token: String,
        @Body
        applicationBody: ApplicationByIdReceiveRemote
    ): ApplicationsResponseRemote

    @POST("application/add")
    suspend fun addApplication(
        @Header("Bearer-Authorization") token: String,
        @Body
        applicationBody: AddApplicationBody
    ): AddApplicationResponse

    @POST("application/update")
    suspend fun updateApplication(
        @Header("Bearer-Authorization") token: String,
        @Body
        updateApplicationBody: UpdateApplicationBody
    ): ApplicationResponseRemote
    @POST("application/delete-byId")
    suspend fun deleteApplicationById(
        @Header("Bearer-Authorization") token: String,
        @Body
        deleteApplicationBody: ApplicationByIdReceiveRemote
    ): ApplicationResponseRemote

    @GET("status/get")
    suspend fun getAllStatuses(): StatusResponseRemote
}