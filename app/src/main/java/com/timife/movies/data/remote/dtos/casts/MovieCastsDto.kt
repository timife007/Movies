package com.timife.movies.data.remote.dtos.casts


import com.squareup.moshi.Json

data class MovieCastsDto(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "cast")
    val castDto: List<CastDto>?,
    @field:Json(name = "crew")
    val crewDto: List<CrewDto>?
)