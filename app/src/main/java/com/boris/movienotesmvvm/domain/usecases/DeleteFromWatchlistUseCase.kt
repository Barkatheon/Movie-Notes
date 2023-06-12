package com.boris.movienotesmvvm.domain.usecases

import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository

class DeleteFromWatchlistUseCase(private val movieLocalRepository: MovieLocalRepository) {

    suspend fun execute(movie : Movie){
        movieLocalRepository.deleteMovie(movie)
    }
}