package com.boris.movienotesmvvm.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.data.repository.MovieRepositoryImpl
import com.boris.movienotesmvvm.data.storage.remote.response.MovieResponse
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    ViewModel() {

    private val _stateFlowData = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val stateFlowData
        get() = _stateFlowData.asStateFlow()

    init {
        getStateFlowData()
        Log.i("myLog", "init of viewModel")
    }


    fun fetchPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        Log.i("myLog", "viewModel fetch popular movies worked")
        try {
            delay(3000)
            val data = getPopularMoviesUseCase.execute().movies
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.stackTraceToString() ?: "Unknown Error"))
        }

    }

    fun getStateFlowData() = viewModelScope.launch {
        Log.i("myLog", "viewModel get state flow data worked")
        fetchPopularMovies().collectLatest {
            _stateFlowData.value = it
        }
    }
}