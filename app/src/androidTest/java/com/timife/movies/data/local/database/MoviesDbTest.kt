package com.timife.movies.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.timife.movies.data.local.model.MoviesEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MoviesDbTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var dao: MoviesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertMovies() {
        dao.upsertMovies(movieEntities)
        assertThat(dao.getPagedMovies()).isNotNull()
    }

    @Test
    fun clearMovies() {
        dao.upsertMovies(movieEntities)
        assertThat(dao.getPagedMovies()).isNotNull()
        dao.clearMovies()
    }


    @Test
    fun updateMovie() {
        dao.upsertMovies(movieEntities)
        val isFavourite = true
        val id = 1
        dao.updateMovie(1, isFavourite)
    }

    @Test
    fun getPagedMovies() {
        dao.upsertMovies(movieEntities)
        assertThat(dao.getPagedMovies()).isNotNull()
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