package com.timife.movies.data.pagination

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
                        remoteKeyDao.getRemoteKey().loadKey!! + 1
                    }
                }
            }
            val movies = api.discoverMovies(page = loadKey)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.dao.clearMovies()
                    remoteKeyDao.deleteRemoteKey()
                }
                val moviesEntity = movies.movieDtos.map {
                    it.toMoviesEntity()
                }
                database.dao.upsertMovies(moviesEntity)
                remoteKeyDao.insertOrReplace(RemoteKey("", movies.page))
            }
            MediatorResult.Success(endOfPaginationReached = movies.movieDtos.isEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}