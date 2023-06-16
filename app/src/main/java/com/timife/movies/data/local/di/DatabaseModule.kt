package com.timife.movies.data.local.di

import android.app.Application
import androidx.room.Room
import com.timife.movies.data.local.database.MovieDao
import com.timife.movies.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movies.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(
        moviesDatabase: MovieDatabase,
    ): MovieDao {
        return moviesDatabase.dao
    }
}