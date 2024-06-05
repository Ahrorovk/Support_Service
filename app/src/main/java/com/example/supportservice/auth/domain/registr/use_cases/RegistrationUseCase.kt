package com.example.supportservice.auth.domain.registr.use_cases

import com.example.supportservice.auth.domain.AuthRepository
import com.example.supportservice.auth.domain.auth.models.LoginResponseRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationReceiveRemote
import com.example.supportservice.auth.domain.registr.models.RegistrationResponseRemote
import com.example.supportservice.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(registerBody: RegistrationReceiveRemote): Flow<Resource<RegistrationResponseRemote>> =
        flow {
            try {
                emit(Resource.Loading<RegistrationResponseRemote>())
                val response = repository.registration(registerBody)
                emit(Resource.Success<RegistrationResponseRemote>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<RegistrationResponseRemote>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<RegistrationResponseRemote>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<RegistrationResponseRemote>("${e.message}"))
            }
        }
}