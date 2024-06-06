package com.example.supportservice.main.data.network.remote

import com.example.supportservice.main.domain.main.models.ApplicationsResponseRemote
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApi {

    @GET("application/get/byEmail")
    suspend fun getApplicationsByEmail(
        @Header("Bearer-Authorization") token: String
    ): ApplicationsResponseRemote
}