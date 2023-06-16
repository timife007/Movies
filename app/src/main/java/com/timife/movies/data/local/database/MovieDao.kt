package com.timife.movies.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.timife.movies.data.local.model.MoviesEntity

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovies(
        moviesEntity: List<MoviesEntity>,
    )

    @Query("DELETE FROM moviesEntity")
    suspend fun clearMovies()

    @Query("SELECT * FROM moviesEntity")
    fun getPagedMovies(): PagingSource<Int, MoviesEntity>

}