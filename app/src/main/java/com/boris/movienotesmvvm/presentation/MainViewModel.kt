package com.boris.movienotesmvvm.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.data.repository.MovieRepositoryImpl
import com.boris.movienotesmvvm.data.storage.remote.response.Movie
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    val movieRepositoryImpl: MovieRepositoryImpl by lazy { MovieRepositoryImpl() }
    val getPopularMoviesUseCase: GetPopularMoviesUseCase by lazy {
        GetPopularMoviesUseCase(
            movieRepositoryImpl
        )
    }


    var stateFlowData = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())

    //var dataStateLive = getPopularMoviesUseCase.execute().asLiveData()
    //var stateFlowData = getPopularMoviesUseCase.execute()
    init {
        getStateFlowData()
        Log.i("myLog", "init of viewModel")
    }


    fun fetchPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        Log.i("myLog", "viewModel fetch poupular movies worked")
        try {
            emit(Resource.Loading())
            delay(3000)
            val data = getPopularMoviesUseCase.execute().results
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }

    }

    fun getStateFlowData() = viewModelScope.launch {
        Log.i("myLog", "viewModel get state flow data worked")
        fetchPopularMovies().collectLatest {
            stateFlowData.value = it
        }
    }
}