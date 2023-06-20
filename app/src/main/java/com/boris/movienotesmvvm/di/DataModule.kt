package com.boris.movienotesmvvm.di

import android.content.Context
import androidx.room.Room
import com.boris.movienotesmvvm.common.Constants
import com.boris.movienotesmvvm.common.Constants.MOVIE_DATABASE
import com.boris.movienotesmvvm.data.repository.MovieLocalRepositoryImpl
import com.boris.movienotesmvvm.data.repository.MovieRepositoryImpl
import com.boris.movienotesmvvm.data.storage.local.MovieDatabase
import com.boris.movienotesmvvm.data.storage.remote.service.MovieService
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePopularMovieService(): MovieService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieService: MovieService): MovieRepository {
        return MovieRepositoryImpl(movieService = movieService)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(appContext, MovieDatabase::class.java, MOVIE_DATABASE).build()

    }

    @Provides
    @Singleton
    fun provideMovieLocalRepository(movieDatabase: MovieDatabase): MovieLocalRepository {
        return MovieLocalRepositoryImpl(movieDao = movieDatabase.movieDao())
    }

}