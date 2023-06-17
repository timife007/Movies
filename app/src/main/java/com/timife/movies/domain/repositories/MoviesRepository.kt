package com.timife.movies.domain.repositories

import androidx.paging.PagingData
import com.timife.movies.domain.model.Cast
import com.timife.movies.domain.model.Movie
import com.timife.movies.domain.model.MovieDetails
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface MoviesRepository {
    fun getPagedData(): Flowable<PagingData<Movie>>

    fun getMovieDetails(
        id:Int
    ): Observable<MovieDetails>

    fun getMovieCasts(
        id:Int
    ):Observable<List<Cast>>
}