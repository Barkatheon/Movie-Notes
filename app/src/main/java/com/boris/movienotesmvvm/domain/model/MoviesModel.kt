package com.boris.movienotesmvvm.domain.model

import com.boris.movienotesmvvm.data.storage.remote.response.MovieResponse
import com.google.gson.annotations.SerializedName

class MoviesModel(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
) {
}