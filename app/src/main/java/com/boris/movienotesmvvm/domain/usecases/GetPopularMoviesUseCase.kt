package com.boris.movienotesmvvm.domain.usecases

import com.boris.movienotesmvvm.data.storage.remote.response.Movie
import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {

    fun execute() : Flow<List<Movie>> = flow {
        emit(movieRepository.getMovies().results)
    }

}