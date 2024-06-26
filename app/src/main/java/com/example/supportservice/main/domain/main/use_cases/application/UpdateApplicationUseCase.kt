package com.example.supportservice.main.domain.main.use_cases.application

import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.application.UpdateApplicationBody
import com.example.supportservice.main.domain.main.models.application.ApplicationResponseRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateApplicationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(
        token: String,
        applicationBody: UpdateApplicationBody
    ): Flow<Resource<ApplicationResponseRemote>> =
        flow {
            try {
                emit(Resource.Loading<ApplicationResponseRemote>())
                val response = repository.updateApplication(token, applicationBody)
                emit(Resource.Success<ApplicationResponseRemote>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<ApplicationResponseRemote>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<ApplicationResponseRemote>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<ApplicationResponseRemote>("${e.message}"))
            }
        }
}