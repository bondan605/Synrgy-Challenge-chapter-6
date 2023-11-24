package com.example.movies_ch6_binar.Models.usecase.getuserdata

import com.example.movies_ch6_binar.data.local.entity.toUser
import com.example.movies_ch6_binar.Models.model.User
import com.example.movies_ch6_binar.Models.repository.AuthRepository
import com.example.movies_ch6_binar.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val user = repository.getUserData(email).toUser()
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}