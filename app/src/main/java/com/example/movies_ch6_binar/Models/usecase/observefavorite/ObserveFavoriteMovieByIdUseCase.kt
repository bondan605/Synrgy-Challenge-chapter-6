package com.example.movies_ch6_binar.Models.usecase.observefavorite

import com.example.movies_ch6_binar.data.local.entity.toMovie
import com.example.movies_ch6_binar.Models.model.Movie
import com.example.movies_ch6_binar.Models.repository.FavoriteRepository
import com.example.movies_ch6_binar.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserveFavoriteMovieByIdUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.observeFavoriteMovieById(userId).map { it.toMovie() }
            emit(Resource.Success(movie))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}