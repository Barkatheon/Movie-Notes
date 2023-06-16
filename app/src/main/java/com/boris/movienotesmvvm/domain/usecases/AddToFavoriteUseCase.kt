package com.boris.movienotesmvvm.domain.usecases

import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository

class AddToFavoriteUseCase(private val movieLocalRepository: MovieLocalRepository) {

    suspend fun execute(movie:Movie){
        movie.isFavorite = true
        movieLocalRepository.saveMovie(movie)
    }
}