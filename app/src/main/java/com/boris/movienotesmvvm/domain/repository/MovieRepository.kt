package com.boris.movienotesmvvm.domain.repository

import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import com.boris.movienotesmvvm.domain.model.MoviesModel

interface MovieRepository {

    suspend fun getMovies(page : Int) : MoviesModel
}