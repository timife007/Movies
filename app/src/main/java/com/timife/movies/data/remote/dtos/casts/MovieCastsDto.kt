package com.timife.movies.data.remote.dtos.casts


import com.squareup.moshi.Json

data class MovieCastsDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "cast")
    val castDto: List<CastDto>,
    @Json(name = "crew")
    val crewDto: List<CrewDto>
)