package com.boris.movienotesmvvm.presentation.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.databinding.FragmentDetailBinding
import com.boris.movienotesmvvm.domain.model.Movie
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val movieId: DetailFragmentArgs by navArgs()
    private lateinit var currentMovie: Movie
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.movie_details)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            detailViewModel.fetchMovieDetail(movieId = movieId.id)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                detailViewModel.movieDetailStateFlow.collectLatest { state ->

                    when (state) {
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Snackbar.make(
                                view,
                                "${state.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }

                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            state.data?.let { currentMovie = it }
                            binding.progressBar.visibility = View.GONE

                            binding.titleDetail.text = currentMovie.title
                            binding.overviewDetail.text = currentMovie.overview
                            Glide.with(requireContext())
                                .load(currentMovie.posterPath)
                                .placeholder(R.drawable.baseline_local_movies_24)
                                .into(binding.imageDetail)

                            if (currentMovie.isWatchlist) {
                                binding.iconWatchlist.setImageResource(R.drawable.bookmark_added)
                            } else {
                                binding.iconWatchlist.setImageResource(R.drawable.bookmark_empty)
                            }
                            if (currentMovie.isFavorite) {
                                binding.iconFavorite.setImageResource(R.drawable.favorite_added)
                            } else {
                                binding.iconFavorite.setImageResource(R.drawable.favorite_empty)
                            }


                        }

                    }

                }

            }
        }

        binding.iconFavorite.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                if (!currentMovie.isFavorite) {
                    detailViewModel.addFavoriteMovie(currentMovie)
                } else {
                    detailViewModel.deleteFavoriteMovie(currentMovie)
                }
            }
        }
        binding.iconWatchlist.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                if (!currentMovie.isWatchlist) {
                    detailViewModel.addWatchlistMovie(currentMovie)
                } else {
                    detailViewModel.deleteWatchlistMovie(currentMovie)
                }
            }

        }

    }


}