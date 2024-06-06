package com.example.supportservice.user.di

import com.example.supportservice.core.util.Constants
import com.example.supportservice.main.data.network.MainRepositoryImpl
import com.example.supportservice.main.data.network.remote.MainApi
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.user.data.network.UserRepositoryImpl
import com.example.supportservice.user.data.network.remote.UserApi
import com.example.supportservice.user.domain.UserRepository
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
class UserModule {

    @Singleton
    @Provides
    fun provideUserApi(): UserApi =
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
            .create(UserApi::class.java)


    @Singleton
    @Provides
    fun provideUserRepository(api: UserApi): UserRepository = UserRepositoryImpl(api)
}