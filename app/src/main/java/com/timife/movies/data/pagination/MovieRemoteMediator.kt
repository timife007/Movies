package com.timife.movies.data.pagination

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.rxjava3.RxRemoteMediator
import androidx.room.withTransaction
import com.timife.movies.data.local.database.MovieDatabase
import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.mappers.toMoviesEntity
import com.timife.movies.data.remote.MoviesApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val api: MoviesApi,
    private val database: MovieDatabase,
) : RxRemoteMediator<Int, MoviesEntity>() {
    private val remoteKeyDao = database.keyDao
    private val moviesDao = database.dao

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, MoviesEntity>,
    ): Single<MediatorResult> {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                Single.just(RemoteKey("", 0))
            }

            LoadType.PREPEND -> {
                return Single.just(MediatorResult.Success(endOfPaginationReached = true))
            }

            LoadType.APPEND -> {
                remoteKeyDao.getRemoteKey()
            }
        }

        return loadKey
            .subscribeOn(Schedulers.io())
            .flatMap<MediatorResult> { remoteKey ->
                if (loadType != LoadType.REFRESH && remoteKey.loadKey == null) {
                    Single.just(MediatorResult.Success(true))
                } else {
                    Timber.tag("localKey").d(remoteKey.loadKey.toString())
                    api.discoverMovies(page = remoteKey.loadKey!! + 1).firstOrError()
                        .map { response ->
                            database.runInTransaction {
                                if (loadType == LoadType.REFRESH) {
                                    moviesDao.clearMovies()
                                    remoteKeyDao.deleteRemoteKey()
                                }
                                val moviesEntity = response.moviesDto?.map {
                                    it.toMoviesEntity()
                                }
                                moviesDao.upsertMovies(moviesEntity ?: emptyList())
                                remoteKeyDao.insertOrReplace(RemoteKey("", response.page))
                                Timber.tag("key").d(response.page.toString())
                            }
                            MediatorResult.Success(
                                endOfPaginationReached = response.moviesDto?.isEmpty() ?: true
                            )
                        }
                }
            }.onErrorResumeNext {
                if (it is IOException || it is HttpException) {
                    Single.just(MediatorResult.Error(it))
                } else {
                    Single.error(it)
                }
            }
    }
}