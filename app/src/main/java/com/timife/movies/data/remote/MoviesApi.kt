package com.timife.movies.data.remote

import com.timife.movies.BuildConfig
import com.timife.movies.data.remote.dtos.casts.MovieCastsDto
import com.timife.movies.data.remote.dtos.details.MovieDetailsDto
import com.timife.movies.data.remote.dtos.movies.DiscoverMovies
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie?language=en-US&include_video=false&sort_by=popularity.desc")
    fun discoverMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
    ): Single<DiscoverMovies>

    @GET("movie/{movie_id}?language=en-US")
    fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Observable<MovieDetailsDto>

    @GET("movie/{movie_id}/credits?language=en-US")
    fun getMovieCast(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Observable<MovieCastsDto>

}