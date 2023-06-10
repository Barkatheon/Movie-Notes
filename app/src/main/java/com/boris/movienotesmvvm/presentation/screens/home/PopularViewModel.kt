package com.boris.movienotesmvvm.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    ViewModel() {

    private val fullMovieList = mutableListOf<Movie>()
    private var currentPage: Int = 1
    private val _stateFlowData = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val stateFlowData
        get() = _stateFlowData.asStateFlow()

    init {
        fetchPopularMovies()
        Log.i("myLog", "init of viewModel")
    }


    fun fetchPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        Log.i("myLog", "viewModel get state flow data worked")
        try {
            _stateFlowData.value = Resource.Loading()
            val data = getPopularMoviesUseCase.execute(currentPage)
            fullMovieList.addAll(data)
            _stateFlowData.value = Resource.Success(fullMovieList)
        } catch (e: Exception) {
            _stateFlowData.value = Resource.Error(e.localizedMessage?.toString() ?: "Unknown Error")
        }
    }

    /*fun fetchPopularMovies() = viewModelScope.launch {
        Log.i("myLog", "viewModel get state flow data worked")
        getPopularMoviesUseCase.execute(currentPage).collectLatest {
            _stateFlowData.value = it
        }
    }*/

    fun fetchNextPage(){
        currentPage++
        Log.i("myLog", "fetchNextPage worked page = $currentPage")
        fetchPopularMovies()
    }
}