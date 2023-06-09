package com.boris.movienotesmvvm.presentation.screens.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.common.Resource
import com.boris.movienotesmvvm.domain.model.Movie
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val movieId: DetailFragmentArgs by navArgs()
    lateinit var currentMovie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTextView = view.findViewById<TextView>(R.id.titleDetail)
        val overviewTextView = view.findViewById<TextView>(R.id.overviewDetail)
        val posterImageView = view.findViewById<ImageView>(R.id.imageDetail)
        val addToWatchlist = view.findViewById<Button>(R.id.addToWatchlistButton)

        if (savedInstanceState == null) {
            detailViewModel.fetchMovieDetail(movieId = movieId.id)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                detailViewModel.movieDetailStateFlow.collectLatest { state ->
                    Log.i("mylog", "flow in Detail screen collected")
                    when (state) {
                        is Resource.Error -> ""
                        is Resource.Loading -> Log.i("mylog", "Detail screen flow loadin collected")
                        is Resource.Success -> {
                            state.data?.let { currentMovie = it }
                            state.data?.let { titleTextView.text = it.title }
                            state.data?.let { overviewTextView.text = it.overview }
                            state.data?.let {
                                Glide.with(requireContext())
                                    .load(it.posterPath)
                                    .placeholder(R.drawable.baseline_local_movies_24)
                                    .into(posterImageView)
                            }

                        }

                    }

                }

            }
        }

        addToWatchlist.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                detailViewModel.saveMovieToWatchlist(currentMovie)

            }

        }

    }


}