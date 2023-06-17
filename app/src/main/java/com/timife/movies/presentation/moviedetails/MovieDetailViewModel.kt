package com.timife.movies.presentation.moviedetails

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.movies.domain.repositories.MoviesRepository
import com.timife.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(MovieDetailsState())

    init {
        savedStateHandle.get<Int>("movieId")?.let { id ->
            val movieDetails = moviesRepository
                .getMovieDetails(id)
                .subscribeOn(Schedulers.io())
            val castItems = moviesRepository
                .getMovieCasts(id)
                .subscribeOn(Schedulers.io())
            state = state.copy(isLoading = true)
            Observable.zip(movieDetails, castItems) { details, casts ->
                Pair(details, casts)
            }.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        state = state.copy(
                            movieDetails = result.first,
                            castsList = result.second,
                            isLoading = false,
                            error = null
                        )
                    },
                    { error ->
                        state = state.copy(
                            isLoading = false,
                            error = error.message,
                            movieDetails = null,
                        )
                    }
                )
        }
    }
}