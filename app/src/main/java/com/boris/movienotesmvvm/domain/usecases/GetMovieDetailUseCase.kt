package com.boris.movienotesmvvm.domain.usecases

import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailUseCase(private val movieRepository: MovieRepository) {

    fun execute(id : Int) : Flow<Resource<Movie>> = flow{
        try {
            emit(Resource.Loading())
            val data = movieRepository.getMovieById(id)
            emit(Resource.Success(data))
        } catch (e : Exception){
            emit(Resource.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }


    }


}