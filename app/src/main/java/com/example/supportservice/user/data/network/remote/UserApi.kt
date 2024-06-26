package com.example.supportservice.user.data.network.remote

import com.example.supportservice.user.domain.models.UserResponseRemote
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("user/get")
    suspend fun getUser(
        @Header("Bearer-Authorization") token: String
    ): UserResponseRemote

    @GET("user/update")
    suspend fun updateUser(
        @Header("Bearer-Authorization") token: String,
        @Body updateUserBody: UserResponseRemote
    ): String
}