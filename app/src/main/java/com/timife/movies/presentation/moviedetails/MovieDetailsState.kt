package com.timife.movies.presentation.moviedetails

import com.timife.movies.domain.model.Cast
import com.timife.movies.domain.model.MovieDetails

data class MovieDetailsState(
    val movieDetails: MovieDetails? = null,
    val castsList: List<Cast> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)