package com.timife.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.mappers.toCast
import com.timife.movies.data.mappers.toMovie
import com.timife.movies.data.mappers.toMovieDetails
import com.timife.movies.data.remote.MoviesApi
import com.timife.movies.domain.model.Cast
import com.timife.movies.domain.model.Movie
import com.timife.movies.domain.model.MovieDetails
import com.timife.movies.domain.repositories.MoviesRepository
import com.timife.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val pager: Pager<Int, MoviesEntity>,
    private val api: MoviesApi,
) : MoviesRepository {
    override fun getPagedData(): Flow<PagingData<Movie>> {
        return pager.flow.map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetails> {
        return try {
            val details = api.getMovieDetails(id = id)
            Resource.Success(details.toMovieDetails())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Error loading Movie Details")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Error loading Movie Details")
        }
    }

    override suspend fun getMovieCasts(id: Int): Resource<List<Cast>> {
        return try {
            val casts = api.getMovieCast(id = id)
            Resource.Success(casts.castDto.map {
                it.toCast()
            })
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load Movie casts")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load Movie casts")
        }
    }
}