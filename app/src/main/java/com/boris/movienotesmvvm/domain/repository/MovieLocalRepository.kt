package com.boris.movienotesmvvm.domain.repository

import com.boris.movienotesmvvm.data.storage.local.MovieDao
import com.boris.movienotesmvvm.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {

    fun getSavedMovies() : Flow<List<Movie>>

    suspend fun saveMovie(movie: Movie)

    suspend fun deleteMovie(movie:Movie)


}