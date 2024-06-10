package com.example.supportservice.main.data.network.remote

import com.example.supportservice.main.domain.main.models.application.AddApplicationBody
import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.application.GetApplicationByIdReceiveRemote
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationBody
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationResponseRemote
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
        applicationBody: GetApplicationByIdReceiveRemote
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
    ): UpdateApplicationResponseRemote

    @GET("status/get")
    suspend fun getAllStatuses(): StatusResponseRemote
}