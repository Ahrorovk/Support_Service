package com.example.supportservice.main.domain.main.use_cases.status

import com.example.supportservice.core.util.Resource
import com.example.supportservice.main.domain.MainRepository
import com.example.supportservice.main.domain.main.models.status.StatusResponseRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllStatusesUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<Resource<StatusResponseRemote>> = flow {
        try {
            emit(Resource.Loading<StatusResponseRemote>())
            val response = repository.getAllStatuses()
            emit(Resource.Success<StatusResponseRemote>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<StatusResponseRemote>(
                    e.message() ?: "Error"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<StatusResponseRemote>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<StatusResponseRemote>("${e.message}"))
        }
    }
}