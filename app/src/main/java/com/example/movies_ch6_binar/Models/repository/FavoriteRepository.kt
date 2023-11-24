package com.example.movies_ch6_binar.Models.repository

import com.example.movies_ch6_binar.data.local.entity.FavoriteEntity
import com.example.movies_ch6_binar.Models.model.Favorite

interface FavoriteRepository {

    suspend fun addFavoriteMovie(movie: FavoriteEntity)

    suspend fun deleteFavoriteMovie(userId: Int, movieId: Int?)

    suspend fun observeFavoriteMovie(userId: Int, movieId: Int): Favorite?

    suspend fun observeFavoriteMovieById(userId: Int): List<FavoriteEntity>
}