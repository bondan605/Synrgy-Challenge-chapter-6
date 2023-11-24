package com.example.movies_ch6_binar.Models.repository

import com.example.movies_ch6_binar.data.remote.dto.DetailDto
import com.example.movies_ch6_binar.data.remote.dto.MovieDto
import com.example.movies_ch6_binar.data.remote.dto.TopRatedDto
import com.example.movies_ch6_binar.data.remote.dto.UpcomingDto

interface MovieRepository {

    suspend fun getMovies(): List<MovieDto>
    suspend fun getMovieById(movieId: Int): DetailDto
    suspend fun getUpcoming(): List<UpcomingDto>
    suspend fun getTopRated(): List<TopRatedDto>
}