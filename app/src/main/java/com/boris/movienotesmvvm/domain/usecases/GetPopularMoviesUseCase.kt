package com.boris.movienotesmvvm.domain.usecases

import android.util.Log
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.data.storage.remote.response.Movie
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {

    fun execute(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val data = movieRepository.getMovies().results
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
            Log.i("mylog", "exception in usecase")
        }
    }

}