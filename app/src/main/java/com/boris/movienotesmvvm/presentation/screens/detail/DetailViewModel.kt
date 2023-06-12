package com.boris.movienotesmvvm.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val deleteFromWatchlistUseCase: DeleteFromWatchlistUseCase
) :
    ViewModel() {


    private val _movieDetailStateFlow = MutableStateFlow<Resource<Movie>>(Resource.Loading())
    val movieDetailStateFlow
        get() = _movieDetailStateFlow.asStateFlow()


    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase.execute(movieId).collect { state ->
                _movieDetailStateFlow.value = state
            }
        }

    }

    suspend fun addToWatchlist(movie: Movie) {
        addToWatchlistUseCase.execute(movie = movie)
    }

    suspend fun deleteFromWatchlist(movie: Movie) {
        deleteFromWatchlistUseCase.execute(movie = movie)
    }
}