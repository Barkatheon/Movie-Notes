package com.boris.movienotesmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.boris.movienotesmvvm.data.repository.MovieRepositoryImpl
import com.boris.movienotesmvvm.data.storage.remote.response.Movie
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase


class MainActivity : AppCompatActivity() {

    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    lateinit var movieRepositoryImpl: MovieRepositoryImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.textView)

        movieRepositoryImpl = MovieRepositoryImpl()
        getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepositoryImpl)
        var listofMovies : LiveData<List<Movie>> = getPopularMoviesUseCase.execute().asLiveData()

        listofMovies.observe(this) {
            if (it != null){
                textView.text = it[0].title
                Log.i("mylog", "observing")
            }
        }
    }
}