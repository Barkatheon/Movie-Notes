package com.boris.movienotesmvvm.domain.usecases

import android.util.Log
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.model.MoviesModel
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {


    suspend fun execute(page : Int) : List<Movie>{
        return movieRepository.getMovies(page).movies
    }
    /*fun execute(page: Int): Flow<Resource<List<Movie>>> = flow {
        Log.i("myLog", "viewModel fetch popular movies worked")
        try {
            emit(Resource.Loading())
            val data = movieRepository.getMovies(page).movies
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }

    }*/
}

