package com.boris.movienotesmvvm.presentation.screens.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
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
class PopularFragment : Fragment() {

    val viewModel: PopularViewModel by viewModels()

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MainRecyclerViewAdapter
    var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recViewMain)
        setUpRecyclerView()


        viewLifecycleOwner.lifecycleScope.launch {
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

    private fun setUpRecyclerView() {
        Log.i("mylog", "setupRecview")
        adapter = MainRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (!isLoading) {
                        viewModel.fetchNextPage()
                    }
                }

            }
        })
    }
}