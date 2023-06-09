package com.boris.movienotesmvvm.data.repository

import com.boris.movienotesmvvm.data.storage.local.MovieDao
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class MovieLocalRepositoryImpl(private val movieDao: MovieDao) : MovieLocalRepository {

    override fun getSavedMovies(): Flow<List<Movie>> {
        return movieDao.getAllSavedMovies()
    }

    override suspend fun saveMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }
}