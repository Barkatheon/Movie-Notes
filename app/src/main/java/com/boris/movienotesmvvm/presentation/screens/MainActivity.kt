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
    var isLoading = false

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
                        is Resource.Loading -> {
                            Log.i("mylog", "flow loadin collected")
                            isLoading = true
                        }
                        is Resource.Success -> {
                            state.data?.let { adapter.setListOfMovies(it) }
                            isLoading = false
                        }
                        is Resource.Error -> {
                            Log.i("mylog", "${state.message}")
                            isLoading = false
                        }


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

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0){
                    if (!isLoading){
                        viewModel.fetchNextPage()
                    }
                }

            }
        })


    }
}
