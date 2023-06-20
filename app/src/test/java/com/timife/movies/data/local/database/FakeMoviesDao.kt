package com.timife.movies.data.local.database

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timife.movies.data.local.model.MoviesEntity
import io.reactivex.rxjava3.core.Completable

class FakeMoviesDao : MoviesDao {

    private val db: MutableList<MoviesEntity> = mutableListOf()
    override fun upsertMovies(moviesEntity: List<MoviesEntity>) {
        db.addAll(moviesEntity)
    }

    override fun updateMovie(id: Int, isFavourite: Boolean): Completable {
        return Completable.fromAction {
            val movie = db.find { it.id == id }
            val newMovie = MoviesEntity(
                id = id,
                original_language = movie?.original_language ?: "",
                poster_path = movie?.poster_path ?: "",
                release_date = movie?.release_date ?: "",
                vote_average = movie?.vote_average ?: 0.0,
                title = movie?.title ?: ""
            )
            if (movie != null) {
                db.remove(movie)
                db.add(newMovie)
            }
        }
    }

    override fun clearMovies() {
        db.clear()
    }

    override fun getPagedMovies(): PagingSource<Int, MoviesEntity> {
        return object : PagingSource<Int, MoviesEntity>() {
            override fun getRefreshKey(state: PagingState<Int, MoviesEntity>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesEntity> {
                val currentPage = params.key ?: 0
                val moviesPerPage = params.loadSize
                val startIndex = currentPage * moviesPerPage
                val endIndex = startIndex + moviesPerPage

                val pagedMovies = db.subList(startIndex, endIndex)

                return LoadResult.Page(
                    data = pagedMovies,
                    prevKey = null,
                    nextKey = currentPage + 1
                )
            }
        }
    }

}