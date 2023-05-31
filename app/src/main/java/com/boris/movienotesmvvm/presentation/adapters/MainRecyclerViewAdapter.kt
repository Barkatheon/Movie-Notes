package com.boris.movienotesmvvm.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.data.storage.remote.response.MovieResponse
import com.boris.movienotesmvvm.domain.model.Movie
import com.bumptech.glide.Glide

class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    val moviesList = ArrayList<Movie>()

    class MainViewHolder(itemView: View) : ViewHolder(itemView) {
        val textViewTitle = itemView.findViewById<TextView>(R.id.titleItem)
        val textViewYear = itemView.findViewById<TextView>(R.id.yearItem)
        val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.textViewTitle.text = moviesList[position].title
        holder.textViewYear.text = moviesList[position].year
        Glide.with(holder.itemView)
            .load(moviesList[position].posterPath)
            .placeholder(R.drawable.baseline_local_movies_24)
            .into(holder.imageViewPoster)

    }

    fun setListOfMovies(list: List<Movie>) {
        if (moviesList.isEmpty()) {
            moviesList.addAll(list)
            notifyDataSetChanged()
        } else {
            val previousListSize = moviesList.size
            moviesList.addAll(list)
            notifyItemChanged(previousListSize, moviesList.size)
        }


    }
}