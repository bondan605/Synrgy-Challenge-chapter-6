package com.example.movies_ch6_binar.Models.model


import java.io.Serializable


data class Detail(
    val id: Int?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val title: String?,
    val voteAverage: Double?,
) : Serializable