package com.timife.movies.data.remote

import com.timife.movies.BuildConfig
import com.timife.movies.data.remote.dtos.casts.MovieCastsDto
import com.timife.movies.data.remote.dtos.details.MovieDetailsDto
import com.timife.movies.data.remote.dtos.movies.DiscoverMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie?language=en-US&include_video=false&sort_by=popularity.desc")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): DiscoverMovies

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieDetailsDto

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCast(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieCastsDto

}