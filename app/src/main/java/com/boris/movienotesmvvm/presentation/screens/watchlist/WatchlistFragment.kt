package com.boris.movienotesmvvm.presentation.screens.watchlist

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
import com.boris.movienotesmvvm.domain.repository.MovieLocalRepository
import com.boris.movienotesmvvm.presentation.adapters.WatchlistAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WatchlistFragment : Fragment() {


    private val watchlistViewModel: WatchlistViewModel by viewModels()
    lateinit var adapter: WatchlistAdapter
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
                watchlistViewModel.stateFlowWatchlist2.collectLatest { movieList ->
                    adapter.setListOfMovies(movieList)

                }
            }
        }
    }


    private fun setupRecyclerView() {
        adapter = WatchlistAdapter()
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
}
