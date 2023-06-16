package com.boris.movienotesmvvm.presentation.screens.watchlist

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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.presentation.adapters.MainRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WatchlistFragment : Fragment(), MainRecyclerViewAdapter.OnItemzClickListener {


    private val watchlistViewModel: WatchlistViewModel by viewModels()
    lateinit var adapter: MainRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recViewWatchlist)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                watchlistViewModel.watchlistStateFlow.collectLatest { movieList ->
                    adapter.setListOfMovies(movieList)

                }
            }
        }
    }


    private fun setupRecyclerView() {
        adapter = MainRecyclerViewAdapter()
        adapter.setOnItemzClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("myLog", "ON DESTROY Watchlist")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("myLog", "ON CreateFragment Watchlist")
    }

    override fun onWatchlistIconClick(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (!item.isWatchlist){
                watchlistViewModel.addWatchlistMovie(item)
            } else{
                watchlistViewModel.deleteWatchlistMovie(item)
            }
        }
        Toast.makeText(requireContext(), "added/deleted movie", Toast.LENGTH_SHORT).show()

    }

    override fun onMovieClick(item: Movie, view: View) {
        val option = item.id
        val action = WatchlistFragmentDirections.actionWatchlistFragmentToDetailFragment(option)
        view.findNavController().navigate(action)
    }

    override fun onFavoriteIconClick(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (!item.isFavorite) {
                watchlistViewModel.addFavoriteMovie(movie = item)
            } else {
                watchlistViewModel.deleteFavoriteMovie(movie = item)
            }
        }
    }

}
