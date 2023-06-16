package com.boris.movienotesmvvm.di

import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import com.boris.movienotesmvvm.domain.usecases.AddToFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.AddToWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromFavoriteUseCase
import com.boris.movienotesmvvm.domain.usecases.DeleteFromWatchlistUseCase
import com.boris.movienotesmvvm.domain.usecases.GetMovieDetailUseCase
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import com.boris.movienotesmvvm.domain.usecases.GetSavedMoviesUseCase
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
    fun provideGetSavedMoviesUseCase(movieLocalRepository: MovieLocalRepository): GetSavedMoviesUseCase{
        return GetSavedMoviesUseCase(movieLocalRepository = movieLocalRepository)
    }

    @Provides
    fun provideAddToWatchlistUseCase(movieLocalRepository: MovieLocalRepository): AddToWatchlistUseCase{
        return AddToWatchlistUseCase(movieLocalRepository = movieLocalRepository)
    }

    @Provides
    fun provideDeleteFromWatchlistUseCase(movieLocalRepository: MovieLocalRepository): DeleteFromWatchlistUseCase{
        return DeleteFromWatchlistUseCase(movieLocalRepository = movieLocalRepository)
    }
    @Provides
    fun provideAddToFavoriteUseCase(movieLocalRepository: MovieLocalRepository): AddToFavoriteUseCase{
        return AddToFavoriteUseCase(movieLocalRepository = movieLocalRepository)
    }
    @Provides
    fun provideDeleteFromFavoriteUseCase(movieLocalRepository: MovieLocalRepository): DeleteFromFavoriteUseCase{
        return DeleteFromFavoriteUseCase(movieLocalRepository = movieLocalRepository)
    }
}