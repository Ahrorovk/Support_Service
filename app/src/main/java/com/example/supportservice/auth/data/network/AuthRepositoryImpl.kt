package com.example.supportservice.auth.data.network

import com.example.supportservice.auth.data.network.remote.AuthApi
import com.example.supportservice.auth.domain.AuthRepository
import com.example.supportservice.auth.domain.auth.models.LoginReceiveRemote
import com.example.supportservice.auth.domain.auth.models.LoginResponseRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationReceiveRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationResponseRemote

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun registration(registerBody: RegistrationReceiveRemote): RegistrationResponseRemote =
        api.registration(registerBody)

    override suspend fun authorization(loginBody: LoginReceiveRemote): LoginResponseRemote =
        api.authorization(loginBody)
}