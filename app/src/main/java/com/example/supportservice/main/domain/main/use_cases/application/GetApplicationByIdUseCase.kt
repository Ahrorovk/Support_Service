package com.example.supportservice.main.domain.main.use_cases.application

import android.util.Log
import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import com.example.supportservice.main.domain.main.models.application.GetApplicationByIdReceiveRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetApplicationByIdUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(
        token: String,
        applicationBody: GetApplicationByIdReceiveRemote
    ): Flow<Resource<ApplicationsResponseRemote>> = flow {
        try {
            Log.e("TAG","ISLOAAAAADIIIINGGG")
            emit(Resource.Loading<ApplicationsResponseRemote>())
            Log.e("TAG","ISLOAAAAAD")
            val response = repository.getApplicationById(token, applicationBody)
            Log.e("TAG","SUCCESSSSSS->$response")
            emit(Resource.Success<ApplicationsResponseRemote>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<ApplicationsResponseRemote>(
                    e.message() ?: "Error"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<ApplicationsResponseRemote>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<ApplicationsResponseRemote>("${e.message}"))
        }
    }
}