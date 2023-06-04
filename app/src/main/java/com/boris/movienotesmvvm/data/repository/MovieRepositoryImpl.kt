package com.boris.movienotesmvvm.data.repository


import com.boris.movienotesmvvm.data.mapper.toMovie
import com.boris.movienotesmvvm.data.mapper.toMoviesModel
import com.boris.movienotesmvvm.data.storage.remote.service.MovieService
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.model.MoviesModel
import com.boris.movienotesmvvm.domain.repository.MovieRepository

class MovieRepositoryImpl(private val movieService: MovieService) : MovieRepository {

    private val apiKey = "467cf966e94812a040f4cd57a8aeb313"

    override suspend fun getMovies(page: Int): MoviesModel {
        return movieService.getPopularMovies(apiKey = apiKey, page = page).toMoviesModel()
    }

    override suspend fun getMovieById(id: Int): Movie {
        return movieService.getMovieDetail(id = id, apiKey = apiKey).toMovie()
    }

}