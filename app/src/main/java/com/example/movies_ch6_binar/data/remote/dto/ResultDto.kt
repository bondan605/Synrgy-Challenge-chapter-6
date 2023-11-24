package com.example.movies_ch6_binar.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val result: List<MovieDto>,
    @SerializedName("resultsUpcoming")
    val resultUp: List<UpcomingDto>,
    @SerializedName("resultsTopRated")
    val resultTop: List<TopRatedDto>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)