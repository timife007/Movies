package com.timife.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.liveData
import androidx.paging.map
import androidx.paging.rxjava3.flowable
import com.timife.movies.data.local.database.MoviesDao
import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.mappers.toCast
import com.timife.movies.data.mappers.toMovie
import com.timife.movies.data.mappers.toMovieDetails
import com.timife.movies.data.remote.MoviesApi
import com.timife.movies.domain.model.Cast
import com.timife.movies.domain.model.Movie
import com.timife.movies.domain.model.MovieDetails
import com.timife.movies.domain.repositories.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

class MoviesRepositoryImpl @Inject constructor(
    private val pager: Pager<Int, MoviesEntity>,
    private val api: MoviesApi,
    private val dao: MoviesDao,
) : MoviesRepository {
    override fun getPagedData(): Flowable<PagingData<Movie>> {
        return pager.flowable.map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }
    }

    override fun getPagedFavourites(): Flowable<PagingData<Movie>> {
        return pager.flowable.map { pagingData ->
            pagingData.map {
                it.toMovie()
            }.filter {
                it.isFavourite
            }
        }
    }

    override fun getMovieDetails(id: Int): Observable<MovieDetails> {
        return api.getMovieDetails(id = id).map {
            it.toMovieDetails()
        }
    }

    override fun getMovieCasts(id: Int): Observable<List<Cast>> {
        return api.getMovieCast(id = id).map { movieCastDto ->
            movieCastDto.castDto?.map {
                it.toCast()
            } ?: listOf(Cast(0, "", "", ""))
        }
    }

    override fun toggleFavourite(id:Int,isFavourite:Boolean) {
        dao.updateMovie(id,isFavourite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}