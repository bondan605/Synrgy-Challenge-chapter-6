package com.example.movies_ch6_binar.Models.model


import com.example.movies_ch6_binar.data.local.entity.FavoriteEntity
import java.io.Serializable


data class Movie(
    val id: Int,
    var userId: Int = 0,
    val popularity: Double?,
    val posterPath: String?,
    val title: String?,
    val voteAverage: Double?,
) : Serializable

fun Movie.toFavoriteEntity(): FavoriteEntity =
    FavoriteEntity(
        id,
        movieId = id,
        userId,
        title,
        posterPath,
        popularity,
        voteAverage
    )
