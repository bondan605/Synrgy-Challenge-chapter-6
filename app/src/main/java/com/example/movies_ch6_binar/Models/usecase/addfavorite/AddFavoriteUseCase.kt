package com.example.movies_ch6_binar.Models.usecase.addfavorite


import com.example.movies_ch6_binar.Models.model.Movie
import com.example.movies_ch6_binar.Models.model.toFavoriteEntity
import com.example.movies_ch6_binar.Models.repository.FavoriteRepository
import com.example.movies_ch6_binar.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(movie: Movie): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val favoriteEntity = movie.toFavoriteEntity()
            val data = favoriteRepository.addFavoriteMovie(favoriteEntity)
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}