package com.example.supportservice.user.domain

import com.example.supportservice.user.domain.models.UserResponseRemote

interface UserRepository {
    suspend fun getUser(
        token: String
    ): UserResponseRemote
}