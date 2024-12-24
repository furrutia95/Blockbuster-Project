package com.nerodev.blockbuster.data.local.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nerodev.blockbuster.data.local.entities.MovieEntity
import com.nerodev.blockbuster.data.local.dao.MovieDao

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
