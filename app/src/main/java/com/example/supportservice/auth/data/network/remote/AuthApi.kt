package com.example.supportservice.auth.data.network.remote

import com.example.supportservice.auth.domain.auth.models.LoginReceiveRemote
import com.example.supportservice.auth.domain.auth.models.LoginResponseRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationReceiveRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationResponseRemote
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("register")
    suspend fun registration(
        @Body
        registerBody: RegistrationReceiveRemote
    ): RegistrationResponseRemote

    @POST("login")
    suspend fun authorization(
        @Body
        loginBody: LoginReceiveRemote
    ): LoginResponseRemote
}