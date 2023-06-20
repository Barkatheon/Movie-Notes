package com.boris.movienotesmvvm.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.AddToFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetMovieDetailUseCase
import com.boris.movienotesmvvm.domain.usecases.GetSavedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val deleteFromWatchlistUseCase: DeleteFromWatchlistUseCase,
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteFromFavoriteUseCase: DeleteFromFavoriteUseCase
) :
    ViewModel() {

    private val _movieDetailStateFlow = MutableStateFlow<Resource<Movie>>(Resource.Loading())
    val movieDetailStateFlow
        get() = _movieDetailStateFlow.asStateFlow()


    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getSavedMoviesUseCase.execute().collect { savedMovies ->
                val databaseMovie = savedMovies.firstOrNull { it.id == movieId }
                if (databaseMovie != null) {
                    _movieDetailStateFlow.value = Resource.Success(databaseMovie)
                } else {
                    getMovieDetailUseCase.execute(movieId).collect { state ->
                        _movieDetailStateFlow.value = state
                    }
                }
            }
        }

    }

    suspend fun addWatchlistMovie(movie: Movie) {
        addToWatchlistUseCase.execute(movie = movie)
    }

    suspend fun deleteWatchlistMovie(movie: Movie) {
        deleteFromWatchlistUseCase.execute(movie = movie)
    }
    suspend fun addFavoriteMovie(movie: Movie){
        addToFavoriteUseCase.execute(movie= movie)
    }
    suspend fun deleteFavoriteMovie(movie: Movie) {
        deleteFromFavoriteUseCase.execute(movie = movie)
    }
}