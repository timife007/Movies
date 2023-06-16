package com.timife.movies.domain.model

data class MovieDetails(
    val backdropPath: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val language: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val tagline: String,
    val title: String,
    val voteAverage: Double
)