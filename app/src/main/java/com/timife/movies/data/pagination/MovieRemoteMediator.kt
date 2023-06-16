package com.timife.movies.data.pagination

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.timife.movies.data.local.database.MovieDatabase
import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.mappers.toMoviesEntity
import com.timife.movies.data.remote.MoviesApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val api: MoviesApi,
    private val database: MovieDatabase,
) : RemoteMediator<Int, MoviesEntity>() {
    private val remoteKeyDao = database.keyDao
    private val moviesDao = database.dao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviesEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKey()
                    }
                    if (remoteKey.loadKey == null) {
                        1
                    } else {
                        remoteKeyDao.getRemoteKey().loadKey!! + 1 //2
                    }
                }
            }
            val movies = api.discoverMovies(page = loadKey)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDao.clearMovies()
                    remoteKeyDao.deleteRemoteKey()
                }
                val moviesEntity = movies.moviesDto?.map {
                    it.toMoviesEntity()
                }
                moviesDao.upsertMovies(moviesEntity ?: emptyList())
                remoteKeyDao.insertOrReplace(RemoteKey("", movies.page))
            }
            MediatorResult.Success(endOfPaginationReached = movies.moviesDto?.isEmpty() ?: true)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}