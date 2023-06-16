package com.boris.movienotesmvvm.domain.usecases

import android.util.Log
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class GetSavedMoviesUseCase(private val movieLocalRepository: MovieLocalRepository) {

    fun execute(): Flow<List<Movie>>{
        Log.i("myLog", "getsavedmoviesusecase worked")
        return movieLocalRepository.getSavedMovies()
    }
}