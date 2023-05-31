package com.boris.movienotesmvvm.di

import com.boris.movienotesmvvm.common.Constants
import com.boris.movienotesmvvm.data.repository.MovieRepositoryImpl
import com.boris.movienotesmvvm.data.storage.remote.service.PopularMovieService
import com.boris.movienotesmvvm.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePopularMovieService(): PopularMovieService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PopularMovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(popularMovieService: PopularMovieService): MovieRepository {
        return MovieRepositoryImpl(popularMovieService)
    }


}