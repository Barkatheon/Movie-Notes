package com.boris.movienotesmvvm.domain.usecases

import android.util.Log
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class GetWatchlistMoviesUseCase(private val movieLocalRepository: MovieLocalRepository) {

    fun execute(): Flow<List<Movie>>{
        Log.i("myLog", "getwatchlistusecase worked")
        return movieLocalRepository.getSavedMovies()
    }
}