package com.boris.movienotesmvvm.data.storage.remote

import com.boris.movienotesmvvm.data.storage.remote.service.PopularMovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Client {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun movieService() : PopularMovieService{
        return retrofit.create(PopularMovieService::class.java)
    }
}