package com.example.movies_ch6_binar.Models.usecase.registeruser

import com.example.movies_ch6_binar.Models.model.User
import com.example.movies_ch6_binar.Models.model.toUserEntity
import com.example.movies_ch6_binar.Models.repository.AuthRepository
import com.example.movies_ch6_binar.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(user: User): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading())
            val userEntity = user.toUserEntity()
            val data = repository.register(userEntity)
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}