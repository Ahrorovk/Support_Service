package com.example.supportservice.auth.domain.auth.use_cases

import com.example.supportservice.auth.domain.AuthRepository
import com.example.supportservice.auth.domain.auth.models.LoginReceiveRemote
import com.example.supportservice.auth.domain.auth.models.LoginResponseRemote
import com.example.supportservice.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(loginBody: LoginReceiveRemote): Flow<Resource<LoginResponseRemote>> =
        flow {
            try {
                emit(Resource.Loading<LoginResponseRemote>())
                val response = repository.authorization(loginBody)
                emit(Resource.Success<LoginResponseRemote>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<LoginResponseRemote>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<LoginResponseRemote>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<LoginResponseRemote>("${e.message}"))
            }
        }
}