package com.boris.movienotesmvvm.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.data.repository.MovieRepositoryImpl
import com.boris.movienotesmvvm.data.storage.remote.response.Movie
import com.boris.movienotesmvvm.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.textView)
        textView.setOnClickListener {
            viewModel.getStateFlowData()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlowData.collectLatest { state ->
                    Log.i("mylog", "flow in mainscreen collected")
                    when (state) {
                        is Resource.Error -> textView.text = state.message
                        is Resource.Loading -> textView.text = "Loading"
                        is Resource.Success -> textView.text =
                            state.data?.get(0)?.title ?: "no data from server"
                    }
                }
            }

        }

    }
}
