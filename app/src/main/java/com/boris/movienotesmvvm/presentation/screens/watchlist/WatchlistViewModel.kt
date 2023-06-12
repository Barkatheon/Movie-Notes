package com.boris.movienotesmvvm.presentation.screens.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetWatchlistMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistMoviesUseCase: GetWatchlistMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val deleteFromWatchlistUseCase: DeleteFromWatchlistUseCase
) :
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
    suspend fun addToWatchlist(movie:Movie){
        addToWatchlistUseCase.execute(movie = movie)
    }
    suspend fun deleteFromWatchlist(movie:Movie){
        deleteFromWatchlistUseCase.execute(movie = movie)
    }
}