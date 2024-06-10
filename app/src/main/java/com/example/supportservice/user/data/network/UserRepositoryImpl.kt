package com.example.supportservice.user.data.network

import com.example.supportservice.user.data.network.remote.UserApi
import com.example.supportservice.user.domain.UserRepository
import com.example.supportservice.user.domain.models.UserResponseRemote
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun getUser(token: String): UserResponseRemote = api.getUser(token)
    override suspend fun updateUser(token: String, updateUserBody: UserResponseRemote): String =
        api.updateUser(token, updateUserBody)
}