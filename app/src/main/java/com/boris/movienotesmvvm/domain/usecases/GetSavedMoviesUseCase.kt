package com.boris.movienotesmvvm.domain.usecases

import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class GetSavedMoviesUseCase(private val movieLocalRepository: MovieLocalRepository) {

    fun execute(): Flow<List<Movie>>{
        return movieLocalRepository.getSavedMovies()
    }
}