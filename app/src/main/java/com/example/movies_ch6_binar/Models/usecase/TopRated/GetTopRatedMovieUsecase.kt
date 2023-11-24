package com.example.movies_ch6_binar.Models.usecase.TopRated

import com.example.movies_ch6_binar.Models.model.Movie
import com.example.movies_ch6_binar.Models.repository.MovieRepository
import com.example.movies_ch6_binar.data.remote.dto.toTop
import com.example.movies_ch6_binar.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTopRatedMovieUsecase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getTopRated().map { it.toTop() }
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connectivity"))
        }
    }
}