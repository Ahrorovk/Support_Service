package com.example.supportservice.main.domain.main.use_cases.application

import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.application.ApplicationsResponseRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetApplicationsByEmailUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(token: String): Flow<Resource<ApplicationsResponseRemote>> = flow {
        try {
            emit(Resource.Loading<ApplicationsResponseRemote>())
            val response = repository.getApplicationsByEmail("$token")
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