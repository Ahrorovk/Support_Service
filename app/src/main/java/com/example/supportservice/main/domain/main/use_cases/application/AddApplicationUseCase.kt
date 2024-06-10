package com.example.supportservice.main.domain.main.use_cases.application

import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.application.AddApplicationBody
import com.example.supportservice.main.domain.main.models.application.AddApplicationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddApplicationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(
        token: String,
        applicationBody: AddApplicationBody
    ): Flow<Resource<AddApplicationResponse>> =
        flow {
            try {
                emit(Resource.Loading<AddApplicationResponse>())
                val response = repository.addApplication(token, applicationBody)
                emit(Resource.Success<AddApplicationResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<AddApplicationResponse>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<AddApplicationResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<AddApplicationResponse>("${e.message}"))
            }
        }
}