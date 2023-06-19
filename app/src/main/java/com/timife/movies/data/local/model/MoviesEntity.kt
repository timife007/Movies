package com.timife.movies.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesEntity")
data class MoviesEntity(
    @PrimaryKey val id: Int,
    val original_language: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val isFavourite:Boolean = false
)