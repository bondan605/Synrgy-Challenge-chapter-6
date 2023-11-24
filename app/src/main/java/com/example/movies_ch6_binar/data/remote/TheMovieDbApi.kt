package com.example.movies_ch6_binar.data.remote.dto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): DetailDto

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): ResultDto

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): ResultDto

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String
    ): ResultDto
}