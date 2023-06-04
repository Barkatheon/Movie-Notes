package com.boris.movienotesmvvm.data.storage.remote.service

import com.boris.movienotesmvvm.data.storage.remote.response.MovieResponse
import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MoviesRemoteResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): MovieResponse
}