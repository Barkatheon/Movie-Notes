package com.boris.movienotesmvvm.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val year: String,
    val posterPath: String,
    val overview: String,
    var isWatchlist: Boolean = false

)
