package com.boris.movienotesmvvm.data.storage.remote.service

import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieService {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey:String, @Query("page") page : Int) : MoviesRemoteResponse
}