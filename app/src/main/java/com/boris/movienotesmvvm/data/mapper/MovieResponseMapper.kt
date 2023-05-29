package com.boris.movienotesmvvm.data.mapper

import com.boris.movienotesmvvm.data.storage.remote.response.MovieResponse
import com.boris.movienotesmvvm.data.storage.remote.response.MoviesRemoteResponse
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.model.MoviesModel

fun MovieResponse.toMovie() : Movie {
    return Movie(
        id = id,
        title = title,
        year = releaseDate,
        posterPath = posterPath
    )
}
fun MoviesRemoteResponse.toMoviesModel(): MoviesModel{
    val mappedResults = results.map { it.toMovie()}
    return MoviesModel(
        page = page,
        movies = mappedResults,
        totalPages = totalPages,
        totalResults = totalResults
    )
}