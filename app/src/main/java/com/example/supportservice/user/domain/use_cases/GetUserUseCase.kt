package com.example.supportservice.user.domain.use_cases

import com.example.supportservice.core.util.Resource
import com.example.supportservice.user.domain.UserRepository
import com.example.supportservice.user.domain.models.UserResponseRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(token: String): Flow<Resource<UserResponseRemote>> = flow {
        try {
            emit(Resource.Loading<UserResponseRemote>())
            val response = repository.getUser(token)
            emit(Resource.Success<UserResponseRemote>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<UserResponseRemote>(
                    e.message() ?: "Error"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<UserResponseRemote>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<UserResponseRemote>("${e.message}"))
        }
    }
}