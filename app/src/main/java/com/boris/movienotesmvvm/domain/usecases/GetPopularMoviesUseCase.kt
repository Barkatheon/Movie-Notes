package com.boris.movienotesmvvm.domain.usecases


import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieRepository


class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(page : Int) : List<Movie>{
        return movieRepository.getMovies(page).movies
    }
}

