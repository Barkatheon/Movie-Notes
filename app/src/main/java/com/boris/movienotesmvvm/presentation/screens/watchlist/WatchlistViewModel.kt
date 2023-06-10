package com.boris.movienotesmvvm.presentation.screens.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.GetWatchlistMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(private val getWatchlistMoviesUseCase: GetWatchlistMoviesUseCase) :
    ViewModel() {

    /*private var _stateFlowWatchlist = getWatchlistMoviesUseCase.execute()
    val stateFlowWatchlist
        get() = _stateFlowWatchlist*/


    private var _stateFlowWatchlist2 = MutableStateFlow<List<Movie>>(emptyList())
    val stateFlowWatchlist2
        get() = _stateFlowWatchlist2.asStateFlow()

    init {
        fetchWatchlistMovies()
    }
    fun fetchWatchlistMovies() = viewModelScope.launch {
        getWatchlistMoviesUseCase.execute().collect { movieList ->
            _stateFlowWatchlist2.value = movieList
        }


    }
}