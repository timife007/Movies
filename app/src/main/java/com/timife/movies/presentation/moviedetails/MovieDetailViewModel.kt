package com.timife.movies.presentation.moviedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.movies.domain.repositories.MoviesRepository
import com.timife.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(MovieDetailsState())

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("id") ?: return@launch
            state = state.copy(isLoading = true)

            val movieDetails = async { moviesRepository.getMovieDetails(id) }
            val casts = async { moviesRepository.getMovieCasts(id) }
            when (val details = movieDetails.await()) {
                is Resource.Success -> {
                    state = state.copy(movieDetails = details.data, isLoading = false, error = null)
                }

                is Resource.Error -> {
                    state =
                        state.copy(isLoading = false, error = details.message, movieDetails = null)
                }

                else -> Unit
            }

            when (val result = casts.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        castsList = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state =
                        state.copy(isLoading = false, error = result.message, movieDetails = null)
                }
                else -> Unit
            }
        }
    }

}