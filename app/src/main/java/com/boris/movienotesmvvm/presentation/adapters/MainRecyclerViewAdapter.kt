package com.boris.movienotesmvvm.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.boris.movienotesmvvm.R
import com.boris.movienotesmvvm.domain.model.Movie
import com.boris.movienotesmvvm.presentation.screens.home.PopularFragmentDirections

import com.bumptech.glide.Glide

class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    val moviesList = ArrayList<Movie>()

    interface OnItemzClickListener {
        fun onItemzClick(item: Movie)
        fun onItem2Click(item: Movie, view: View)
    }

    private var itemzClickListener: OnItemzClickListener? = null

    fun setOnItemzClickListener(listener: OnItemzClickListener) {
        itemzClickListener = listener
    }

    class MainViewHolder(itemView: View) : ViewHolder(itemView) {
        val textViewTitle = itemView.findViewById<TextView>(R.id.titleItem)
        val textViewYear = itemView.findViewById<TextView>(R.id.yearItem)
        val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageItem)
        val iconWatchlist = itemView.findViewById<ImageView>(R.id.iconWatchlist)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.textViewTitle.text = movie.title
        holder.textViewYear.text = position.toString()
        if (movie.isWatchlist) {
            holder.iconWatchlist.setImageResource(R.drawable.bookmark_added)
        } else {
            holder.iconWatchlist.setImageResource(R.drawable.bookmark_empty)
        }

        Glide.with(holder.itemView)
            .load(movie.posterPath)
            .placeholder(R.drawable.baseline_local_movies_24)
            .into(holder.imageViewPoster)

        holder.itemView.setOnClickListener { itemView ->
            itemzClickListener?.onItem2Click(item = movie, view = itemView)
        }

        holder.iconWatchlist.setOnClickListener {
            itemzClickListener?.onItemzClick(movie)
            notifyItemChanged(holder.bindingAdapterPosition)
        }

    }

    fun setListOfMovies(list: List<Movie>) {
        if (moviesList.isEmpty()) {
            Log.i("mylog", "adapter movielist empty")
            moviesList.addAll(list)
            notifyDataSetChanged()
        } else {
            val previousListSize = moviesList.size
            moviesList.clear()
            moviesList.addAll(list)
            notifyDataSetChanged()

        }
        Log.i("mylog", "adapter movie list size ${moviesList.size}")

    }
}