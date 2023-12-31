package com.timife.movies.presentation.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.timife.movies.domain.model.Movie
import com.timife.movies.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
) : ViewModel() {
    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> get() = _movies

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> get() = _filterState


    init {
        fetchPagedData()
    }

    fun filter(item:String){
        _filterState.value = FilterState(filterItem = item)
    }

    fun fetchPagedData() = repository
        .getPagedData()
        .cachedIn(viewModelScope)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            _movies.value = it
        }

    fun fetchPagedFavourites() = repository
        .getPagedFavourites()
        .cachedIn(viewModelScope)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            _movies.value = it
        }

    fun toggleFavourite(id:Int, isFavourite:Boolean){
        repository.toggleFavourite(id,isFavourite)
    }
}