package com.boris.movienotesmvvm.domain.usecases

import com.boris.movienotesmvvm.domain.model.MoviesModel
import com.boris.movienotesmvvm.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(page: Int): MoviesModel {
        return movieRepository.getMovies(page)
    }

}