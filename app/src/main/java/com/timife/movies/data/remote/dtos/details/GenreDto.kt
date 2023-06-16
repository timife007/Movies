package com.timife.movies.data.remote.dtos.details


import com.squareup.moshi.Json

data class GenreDto(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "name")
    val name: String?
)