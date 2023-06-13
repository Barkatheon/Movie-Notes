package com.boris.movienotesmvvm.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import com.boris.movienotesmvvm.domain.usecases.GetWatchlistMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getWatchlistMoviesUseCase: GetWatchlistMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val deleteFromWatchlistUseCase: DeleteFromWatchlistUseCase
) :
    ViewModel() {

    private val fullMovieList = mutableListOf<Movie>()
    private var currentPage: Int = 1

    private val _stateFlowData = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val stateFlowData
        get() = _stateFlowData.asStateFlow()

    private val _watchlistMovies = MutableStateFlow<List<Movie>>(emptyList())


    init {
        fetchWatchlistMovies()
        fetchPopularMovies()
        Log.i("myLog", "init of viewModel")
    }


    fun updateFullMovieList() {
        if (fullMovieList.isNotEmpty()) {
            fullMovieList.forEach { movie ->
                movie.isWatchlist = _watchlistMovies.value.any {
                    it.id == movie.id
                }
            }
            Log.i("myLog", "updateFullMovieListWorked")

            _stateFlowData.value = Resource.Success(fullMovieList)
        }
    }

    fun fetchPopularMovies() = viewModelScope.launch(Dispatchers.IO) {

        Log.i("myLog", "viewModel get state flow data worked")
        try {
            _stateFlowData.value = Resource.Loading()
            val remoteData = getPopularMoviesUseCase.execute(currentPage)
            Log.i("myLog", "watchlistdata size = ${_watchlistMovies.value.size}")
            fullMovieList.addAll(remoteData)
            updateFullMovieList()
        } catch (e: Exception) {
            _stateFlowData.value = Resource.Error(e.localizedMessage?.toString() ?: "Unknown Error")
        }
    }

    fun fetchWatchlistMovies() = viewModelScope.launch(Dispatchers.IO) {
        getWatchlistMoviesUseCase.execute().collect { savedMovies ->
            _watchlistMovies.value = savedMovies
            updateFullMovieList()

        }
    }

    suspend fun addWatchlistMovie(movie: Movie) {
        addToWatchlistUseCase.execute(movie = movie)
    }

    suspend fun deleteWatchlistMovie(movie: Movie) {
        deleteFromWatchlistUseCase.execute(movie = movie)
    }


    fun fetchNextPage() {
        currentPage++
        Log.i("myLog", "fetchNextPage worked page = $currentPage")
        fetchPopularMovies()
    }
}