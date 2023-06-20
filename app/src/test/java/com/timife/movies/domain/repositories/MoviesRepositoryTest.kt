package com.timife.movies.domain.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.flowable
import com.timife.movies.data.local.database.MoviesDao
import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.mappers.toCast
import com.timife.movies.data.mappers.toMovie
import com.timife.movies.data.mappers.toMovieDetails
import com.timife.movies.data.remote.MoviesApi
import com.timife.movies.data.remote.dtos.casts.CastDto
import com.timife.movies.data.remote.dtos.casts.CrewDto
import com.timife.movies.data.remote.dtos.casts.MovieCastsDto
import com.timife.movies.data.remote.dtos.details.MovieDetailsDto
import com.timife.movies.data.repositories.MoviesRepositoryImpl
import com.timife.movies.domain.model.Genre
import com.timife.movies.domain.model.MovieDetails
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {
    @Mock
    lateinit var pager: Pager<Int, MoviesEntity>

    @Mock
    lateinit var api: MoviesApi

    @Mock
    lateinit var dao: MoviesDao

    lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        moviesRepository = MoviesRepositoryImpl(pager, api, dao)
    }


    @Test
    fun getPagedData()  {
        whenever(pager.flowable).thenReturn(Flowable.just(testPagingData))

        val testObserver = moviesRepository.getPagedData().test()

        testObserver.assertValue(testPagingData.map {
            it.toMovie()
        })
        verify(testObserver)
        testObserver.assertComplete()
    }

    @Test
    fun getMovieDetails() {
        val movieId = 2

        whenever(api.getMovieDetails(movieId)).thenReturn(Observable.just(testMovieDetails))

        val testObserver = moviesRepository.getMovieDetails(movieId).test()

        testObserver.assertValue(testMovieDetails.toMovieDetails())
        testObserver.assertComplete()
    }

    @Test
    fun getMovieCasts() {
        val movieId = 2
        whenever(api.getMovieCast(movieId)).thenReturn(Observable.just(testCastsDto))

        val testObserver = moviesRepository.getMovieCasts(movieId).test()

        testObserver.assertValue(expectedCastDto.map { it.toCast() })
        testObserver.assertComplete()
    }

    companion object {
        val testPagingData = PagingData.from(
            listOf(
                MoviesEntity(
                    1,
                    "Eng",
                    "https:path-one.svg",
                    "24-10-22",
                    "God of war",
                    2.0,
                    isFavourite = true
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
        )
        val movieEntities = listOf(
            MoviesEntity(
                1,
                "Eng",
                "https:path-one.svg",
                "24-10-22",
                "God of war",
                2.0,
                isFavourite = true
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
        val testMovieDetails = MovieDetailsDto(
            false,
            "svg.go",
            "",
            200,
            emptyList(),
            "",
            2,
            "",
            "English",
            "Game of Thrones",
            "",
            3.4,
            "svg.com",
            emptyList(),
            emptyList(),
            "24th August",
            400L,
            200,
            emptyList(),
            "",
            "",
            "GOT",
            false,
            2.3,
            200,
        )
        val testCastsDto = MovieCastsDto(
            id = 2,
            castDto = listOf(
                CastDto(false, 1, "", "", 2, 2, "adventure", "Bob", 1, "", 2.2, "")
            ),
            crewDto = emptyList()
        )
        val expectedCastDto = listOf(
            CastDto(false, 1, "", "", 2, 2, "adventure", "Bob", 1, "", 2.2, "")
        )
    }
}