package com.example.movies_ch6_binar.data.repository


import com.example.movies_ch6_binar.Models.repository.MovieRepository
import com.example.movies_ch6_binar.data.remote.dto.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TheMovieDbApi
) : MovieRepository {

    override suspend fun getMovies(): List<MovieDto> {
        return api.getMovies("acb0afb6699c9aa6945f1d6a3f1ec89c").result
    }

    override suspend fun getUpcoming(): List<UpcomingDto> {
        return api.getUpcomingMovies("acb0afb6699c9aa6945f1d6a3f1ec89c").resultUp
    }

    override suspend fun getTopRated(): List<TopRatedDto> {
        return api.getTopRatedMovies("acb0afb6699c9aa6945f1d6a3f1ec89c").resultTop
    }

    override suspend fun getMovieById(movieId: Int): DetailDto {
        return api.getMovieById(movieId, "acb0afb6699c9aa6945f1d6a3f1ec89c")
    }
}