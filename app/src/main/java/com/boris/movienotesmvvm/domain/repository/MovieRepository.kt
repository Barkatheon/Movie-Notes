package com.boris.movienotesmvvm.domain.repository

import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse

interface MovieRepository {

    suspend fun getMovies() : MoviesRemoteResponse
}