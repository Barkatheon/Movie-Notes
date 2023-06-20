package com.boris.movienotesmvvm.domain.model

class MoviesModel(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
