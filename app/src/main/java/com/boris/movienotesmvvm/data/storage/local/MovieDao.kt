package com.boris.movienotesmvvm.data.storage.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.boris.movienotesmvvm.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_table")
    fun getAllSavedMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies_table where id= :id")
    suspend fun getSavedMovieById(id: Int): Movie

    @Delete
    suspend fun deleteSavedMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)
}