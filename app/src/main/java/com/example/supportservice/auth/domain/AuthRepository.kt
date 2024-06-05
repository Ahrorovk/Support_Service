package com.example.supportservice.auth.domain

import com.example.supportservice.auth.domain.auth.models.LoginReceiveRemote
import com.example.supportservice.auth.domain.auth.models.LoginResponseRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationReceiveRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationResponseRemote

interface AuthRepository {
    suspend fun registration(
        registerBody: RegistrationReceiveRemote
    ): RegistrationResponseRemote


    suspend fun authorization(
        loginBody: LoginReceiveRemote
    ): LoginResponseRemote
}