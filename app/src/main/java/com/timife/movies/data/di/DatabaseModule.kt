package com.timife.movies.data.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.timife.movies.data.local.database.MovieDatabase
import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.pagination.MovieRemoteMediator
import com.timife.movies.data.remote.MoviesApi
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

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMoviesPager(
        moviesDatabase: MovieDatabase,
        moviesApi: MoviesApi,
    ): Pager<Int, MoviesEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = MovieRemoteMediator(moviesApi, moviesDatabase),
            pagingSourceFactory = {
                moviesDatabase.dao.getPagedMovies()
            }
        )
    }
}