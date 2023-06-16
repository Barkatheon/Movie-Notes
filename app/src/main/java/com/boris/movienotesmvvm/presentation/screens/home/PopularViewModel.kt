package com.boris.movienotesmvvm.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.AddToFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import com.boris.movienotesmvvm.domain.usecases.GetSavedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val deleteFromWatchlistUseCase: DeleteFromWatchlistUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase
) :
    ViewModel() {

    private val fullMovieList = mutableListOf<Movie>()
    private var currentPage: Int = 1

    private val _stateFlowData = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val stateFlowData
        get() = _stateFlowData.asStateFlow()

    private val _savedMoviesStateFlow = MutableStateFlow<List<Movie>>(emptyList())

    //Change Detail screen to get one movie from db, not all
    //Change code to prevent repeating of code(example - on click methods)


    init {
        getSavedMovies()
        fetchPopularMovies()
        Log.i("myLog", "init of viewModel")
    }


    fun updateFullMovieList() {
        if (fullMovieList.isNotEmpty()) {
            fullMovieList.forEach { movie ->
                val savedMovie = _savedMoviesStateFlow.value.firstOrNull {
                    it.id == movie.id
                }
                movie.isWatchlist = savedMovie != null && savedMovie.isWatchlist
                movie.isFavorite = savedMovie != null && savedMovie.isFavorite
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
            Log.i("myLog", "watchlistdata size = ${_savedMoviesStateFlow.value.size}")
            fullMovieList.addAll(remoteData)
            updateFullMovieList()
        } catch (e: Exception) {
            _stateFlowData.value = Resource.Error(e.localizedMessage?.toString() ?: "Unknown Error")
        }
    }

    fun getSavedMovies() = viewModelScope.launch(Dispatchers.IO) {
        getSavedMoviesUseCase.execute().collect { savedMovies ->
            _savedMoviesStateFlow.value = savedMovies
            updateFullMovieList()
        }

    }

    suspend fun addWatchlistMovie(movie: Movie) {
        addToWatchlistUseCase.execute(movie = movie)
    }

    suspend fun deleteWatchlistMovie(movie: Movie) {
        deleteFromWatchlistUseCase.execute(movie = movie)
    }

    suspend fun addFavoriteMovie(movie: Movie) {
        addToFavoriteUseCase.execute(movie = movie)
    }

    suspend fun deleteFavoriteMovie(movie: Movie) {
        deleteFromFavoriteUseCase.execute(movie = movie)
    }

    fun fetchNextPage() {
        currentPage++
        Log.i("myLog", "fetchNextPage worked page = $currentPage")
        fetchPopularMovies()
    }
}