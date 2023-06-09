package com.boris.movienotesmvvm.data.storage.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boris.movienotesmvvm.domain.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}