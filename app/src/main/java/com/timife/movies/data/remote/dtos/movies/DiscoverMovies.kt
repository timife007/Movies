package com.timife.movies.data.remote.dtos.movies


import com.squareup.moshi.Json

data class DiscoverMovies(
    @field:Json(name = "page")
    val page: Int?,
    @field:Json(name = "results")
    val moviesDto: List<MovieDto>?,
    @field:Json(name = "total_pages")
    val totalPages: Int?,
    @field:Json(name = "total_results")
    val totalResults: Int?
)