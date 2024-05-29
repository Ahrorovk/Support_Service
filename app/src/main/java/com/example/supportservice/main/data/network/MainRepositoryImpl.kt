package com.example.supportservice.main.data.network

import com.example.supportservice.main.data.network.remote.MainApi
import com.example.supportservice.main.domain.MainRepository

class MainRepositoryImpl(
    private val api: MainApi
): MainRepository {

}