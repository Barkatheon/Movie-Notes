package com.boris.movienotesmvvm.domain.usecases

import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import com.boris.movienotesmvvm.domain.model.MoviesModel
import com.boris.movienotesmvvm.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(): MoviesModel{
        return movieRepository.getMovies()
    }

}