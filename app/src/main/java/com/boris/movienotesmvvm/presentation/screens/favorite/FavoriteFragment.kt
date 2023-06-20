package com.boris.movienotesmvvm.presentation.screens.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class FavoriteFragment : Fragment(), MainRecyclerViewAdapter.OnItemzClickListener {

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private lateinit var adapter: MainRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.favorite_movies)
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recViewFavorite)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteViewModel.favoriteMoviesStateFlow.collectLatest { favoriteMovies ->
                    adapter.setListOfMovies(favoriteMovies)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MainRecyclerViewAdapter()
        adapter.setOnItemzClickListener(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onMovieClick(item: Movie, view: View) {
        val movieId = item.id
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(movieId)
        view.findNavController().navigate(action)
    }
    override fun onWatchlistIconClick(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (item.isWatchlist) {
                favoriteViewModel.deleteWatchlistMovie(movie = item)
            } else {
                favoriteViewModel.addWatchlistMovie(movie = item)
            }
        }

    }
    override fun onFavoriteIconClick(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (!item.isFavorite) {
                favoriteViewModel.addFavoriteMovie(movie = item)
            } else {
                favoriteViewModel.deleteFavoriteMovie(movie = item)
            }
        }
    }


}