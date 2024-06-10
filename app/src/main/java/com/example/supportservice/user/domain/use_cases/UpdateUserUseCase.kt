package com.example.supportservice.user.domain.use_cases

import com.example.supportservice.core.util.Resource
import com.example.supportservice.user.domain.UserRepository
import com.example.supportservice.user.domain.models.UserResponseRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(token: String, body: UserResponseRemote): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            val response = repository.updateUser(token, body)
            emit(Resource.Success<String>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<String>(
                    e.message() ?: "Error"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<String>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<String>("${e.message}"))
        }
    }
}