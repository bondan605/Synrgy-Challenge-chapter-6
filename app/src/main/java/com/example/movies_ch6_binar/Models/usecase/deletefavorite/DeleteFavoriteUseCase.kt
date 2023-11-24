package com.example.movies_ch6_binar.Models.usecase.deletefavorite

import com.example.movies_ch6_binar.Models.repository.FavoriteRepository
import com.example.movies_ch6_binar.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(userId: Int, movieId: Int?): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.deleteFavoriteMovie(userId, movieId)
            emit(Resource.Success(movie))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}