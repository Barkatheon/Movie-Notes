package com.boris.movienotesmvvm.data.repository


import com.boris.movienotesmvvm.data.mapper.toMoviesModel
import com.boris.movienotesmvvm.data.storage.remote.Client.movieService
import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import com.boris.movienotesmvvm.domain.model.MoviesModel
import com.boris.movienotesmvvm.domain.repository.MovieRepository

class MovieRepositoryImpl : MovieRepository {

    private val apiKey =  "467cf966e94812a040f4cd57a8aeb313"

    override suspend fun getMovies(): MoviesModel {
        return movieService().getPopularMovies(apiKey).toMoviesModel()
    }
}