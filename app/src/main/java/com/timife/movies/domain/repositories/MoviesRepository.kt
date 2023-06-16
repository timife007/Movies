package com.timife.movies.domain.repositories

import androidx.paging.PagingData
import com.timife.movies.domain.model.Cast
import com.timife.movies.domain.model.Movie
import com.timife.movies.domain.model.MovieDetails
import com.timife.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPagedData(): Flow<PagingData<Movie>>

    suspend fun getMovieDetails(
        id:Int
    ): Resource<MovieDetails>

    suspend fun getMovieCasts(
        id:Int
    ):Resource<List<Cast>>
}