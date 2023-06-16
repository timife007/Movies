package com.timife.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.pagination.RemoteKey
import com.timife.movies.data.pagination.RemoteKeyDao

@Database(
    entities = [MoviesEntity::class,RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MovieDao
    abstract val keyDao:RemoteKeyDao
}