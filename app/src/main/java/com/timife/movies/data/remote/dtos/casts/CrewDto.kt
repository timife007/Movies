package com.timife.movies.data.remote.dtos.casts


import com.squareup.moshi.Json

data class CrewDto(
    @field:Json(name = "adult")
    val adult: Boolean?,
    @field:Json(name = "credit_id")
    val creditId: String?,
    @field:Json(name = "department")
    val department: String?,
    @field:Json(name = "gender")
    val gender: Int?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "job")
    val job: String?,
    @field:Json(name = "known_for_department")
    val knownForDepartment: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "original_name")
    val originalName: String?,
    @field:Json(name = "popularity")
    val popularity: Double?,
    @field:Json(name = "profile_path")
    val profilePath: String?
)