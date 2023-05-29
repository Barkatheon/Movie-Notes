package com.boris.movienotesmvvm.domain.usecases

import android.util.Log

import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.data.storage.remote.response.Movie
import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(): MoviesRemoteResponse{
        return movieRepository.getMovies()
    }

}