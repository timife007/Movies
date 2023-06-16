package com.timife.movies.domain.model

data class Movie(
    val id: Int? = null,
    val original_language: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double
)