package com.timife.movies.data.local.database

import com.google.common.truth.Truth.assertThat
import com.timife.movies.data.local.model.MoviesEntity
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesDaoTest {

    @Mock
    lateinit var moviesDao: MoviesDao


    @Test
    fun testUpsertMovies(){
        moviesDao.upsertMovies(movieEntities)
        verify(moviesDao).upsertMovies(movieEntities)
    }

    @Test
    fun testClearMovies(){
        moviesDao.upsertMovies(movieEntities)
        moviesDao.clearMovies()
        verify(moviesDao).clearMovies()
    }

    @Test
    fun testUpdateMovies(){
        val movieId = 1
        val isFavourite = true

        moviesDao.updateMovie(movieId, isFavourite)

        verify(moviesDao).updateMovie(movieId, isFavourite)
    }

    companion object {
        val movieEntities = listOf(
            MoviesEntity(
                1,
                "Eng",
                "https:path-one.svg",
                "24-10-22",
                "God of war",
                2.0,
                isFavourite = false
            ),
            MoviesEntity(
                2,
                "Eng",
                "https:path-two.svg",
                "23:11-22",
                "GOT",
                3.2,
                isFavourite = false
            )
        )
    }
}