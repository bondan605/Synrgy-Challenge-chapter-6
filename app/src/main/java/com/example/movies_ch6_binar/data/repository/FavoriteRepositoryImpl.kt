package com.example.movies_ch6_binar.data.repository

import com.example.movies_ch6_binar.data.local.entity.FavoriteEntity
import com.example.movies_ch6_binar.data.local.entity.toFavorite
import com.example.movies_ch6_binar.data.local.room.dao.FavoriteDao
import com.example.movies_ch6_binar.Models.model.Favorite
import com.example.movies_ch6_binar.Models.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun addFavoriteMovie(movie: FavoriteEntity) {
        return favoriteDao.addFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(userId: Int, movieId: Int?) {
        return favoriteDao.removeFavoriteMovie(userId, movieId)
    }

    override suspend fun observeFavoriteMovie(userId: Int, movieId: Int): Favorite? {
        return favoriteDao.observeFavoriteMovie(userId, movieId)?.toFavorite()
    }

    override suspend fun observeFavoriteMovieById(userId: Int): List<FavoriteEntity> {
        return favoriteDao.observerFavoriteMovieById(userId)
    }
}