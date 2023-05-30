package com.boris.movienotesmvvm.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.presentation.adapters.MainRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : MainRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recViewMain)
        setUpRecyclerView()


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlowData.collectLatest { state ->
                    Log.i("mylog", "flow in mainscreen collected")
                    when (state) {
                        is Resource.Error -> Log.i("mylog", "${state.message}")//Todo
                        is Resource.Loading -> Log.i("mylog", "flow loadin collected")//Todo
                        is Resource.Success -> state.data?.let { adapter.setListOfMovies(it) }


                    }
                }
            }

        }

    }
    private fun setUpRecyclerView(){
        Log.i("mylog", "setupRecview")
        adapter = MainRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = adapter
    }
}
