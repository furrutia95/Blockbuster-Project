package com.nerodev.blockbuster.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nerodev.blockbuster.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorite_movies ORDER BY title DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}