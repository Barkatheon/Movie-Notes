package com.boris.movienotesmvvm.domain.repository

import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.model.MoviesModel

interface MovieRepository {

    suspend fun getMovies(page: Int): MoviesModel

    suspend fun getMovieById(id: Int): Movie
}