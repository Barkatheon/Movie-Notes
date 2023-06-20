package com.boris.movienotesmvvm.presentation.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.presentation.adapters.MainRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PopularFragment : Fragment(), MainRecyclerViewAdapter.OnItemzClickListener {

    private val viewModel: PopularViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainRecyclerViewAdapter
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.popular_movies)
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recViewMain)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        setUpRecyclerView()
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        swipeRefresh.setOnRefreshListener {
            viewModel.refreshMovies()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularMoviesStateFlow.collectLatest { state ->

                    when (state) {
                        is Resource.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            isLoading = true
                        }

                        is Resource.Success -> {
                            state.data?.let {
                                adapter.setListOfMovies(it)
                            }
                            progressBar.visibility = View.GONE
                            isLoading = false
                            swipeRefresh.isRefreshing = false
                        }

                        is Resource.Error -> {
                            progressBar.visibility = View.GONE
                            isLoading = false
                            swipeRefresh.isRefreshing = false
                            Snackbar.make(view, "${state.message}", Snackbar.LENGTH_LONG).show()

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

    override fun onMovieClick(item: Movie, view: View) {
        val movieId = item.id
        val action = PopularFragmentDirections.actionPopularFragmentToDetailFragment(movieId)
        view.findNavController().navigate(action)
    }

    override fun onWatchlistIconClick(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (!item.isWatchlist) {
                viewModel.addWatchlistMovie(movie = item)
            } else {
                viewModel.deleteWatchlistMovie(movie = item)
            }
            withContext(Dispatchers.Main) {
                val addedOrDeleted = if (item.isWatchlist) "added to" else "deleted from"
                Toast.makeText(
                    requireContext(),
                    "Movie $addedOrDeleted watchlist",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }
        }

    }

    override fun onFavoriteIconClick(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (!item.isFavorite) {
                viewModel.addFavoriteMovie(movie = item)
            } else {
                viewModel.deleteFavoriteMovie(movie = item)
            }
            withContext(Dispatchers.Main) {
                val addedOrDeleted = if (item.isFavorite) "added to" else "deleted from"
                Toast.makeText(
                    requireContext(),
                    "Movie $addedOrDeleted favorite",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

    }

}