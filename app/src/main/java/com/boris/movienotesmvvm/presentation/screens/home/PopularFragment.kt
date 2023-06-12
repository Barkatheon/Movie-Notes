package com.boris.movienotesmvvm.presentation.screens.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.presentation.adapters.MainRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment : Fragment(), MainRecyclerViewAdapter.OnItemzClickListener {

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
                            state.data?.let {
                                adapter.setListOfMovies(it)
                                Log.i("myLog", "${it.size}")
                            }
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

        adapter = MainRecyclerViewAdapter()
        adapter.setOnItemzClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        Log.i("mylog", "setupRecview with adapter ${adapter.hashCode()}")

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

    override fun onItemzClick(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch {
            if (!item.isWatchlist){
                item.isWatchlist = true
                viewModel.addWatchlistMovie(movie = item)
            } else{
                item.isWatchlist = false
                viewModel.deleteWatchlistMovie(movie = item)

            }
            var addedOrDeleted = if(item.isWatchlist) "added to" else "deleted from"
            Toast.makeText(requireContext(),"Movie $addedOrDeleted watchlist", Toast.LENGTH_SHORT).show()
        }




    }

    override fun onItem2Click(item: Movie, view: View) {
        val movieId = item.id
        val action = PopularFragmentDirections.actionPopularFragmentToDetailFragment(movieId)
        view.findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("myLog", "popularFragmentOndestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("myLog", "popularFragmentOnCreate")


    }


}