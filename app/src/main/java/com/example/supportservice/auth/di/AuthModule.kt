package com.example.supportservice.auth.di

import com.example.supportservice.auth.data.network.AuthRepositoryImpl
import com.example.supportservice.auth.data.network.remote.AuthApi
import com.example.supportservice.auth.domain.AuthRepository
import com.example.supportservice.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(150, TimeUnit.SECONDS)
                    .connectTimeout(150, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(AuthApi::class.java)



    @Singleton
    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository = AuthRepositoryImpl(api)
}