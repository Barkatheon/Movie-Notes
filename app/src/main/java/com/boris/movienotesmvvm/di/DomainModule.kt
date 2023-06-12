package com.boris.movienotesmvvm.di

import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetMovieDetailUseCase
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import com.boris.movienotesmvvm.domain.usecases.GetWatchlistMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetPopularMoviesUseCase(movieRepository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(movieRepository = movieRepository)
    }
    @Provides
    fun provideGetMovieDetailUseCase(movieRepository: MovieRepository): GetMovieDetailUseCase{
        return GetMovieDetailUseCase(movieRepository = movieRepository)
    }

    @Provides
    fun provideAddToWatchlistUseCase(movieLocalRepository: MovieLocalRepository): AddToWatchlistUseCase{
        return AddToWatchlistUseCase(movieLocalRepository = movieLocalRepository)
    }
    @Provides
    fun provideGetWatchlistMoviesUseCase(movieLocalRepository: MovieLocalRepository): GetWatchlistMoviesUseCase{
        return GetWatchlistMoviesUseCase(movieLocalRepository = movieLocalRepository)
    }
    @Provides
    fun provideDeleteFromWatchlistUseCase(movieLocalRepository: MovieLocalRepository): DeleteFromWatchlistUseCase{
        return DeleteFromWatchlistUseCase(movieLocalRepository = movieLocalRepository)
    }
}