package com.timife.movies.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.timife.movies.data.local.model.MoviesEntity
import io.reactivex.rxjava3.core.Completable

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun upsertMovies(
        moviesEntity: List<MoviesEntity>,
    )

    @Query("UPDATE moviesEntity SET isFavourite = :isFavourite WHERE id = :id")
    fun updateMovie(id: Int, isFavourite: Boolean):Completable

    @Query("DELETE FROM moviesEntity")
    fun clearMovies()

    @Query("SELECT * FROM moviesEntity")
    fun getPagedMovies(): PagingSource<Int, MoviesEntity>

}