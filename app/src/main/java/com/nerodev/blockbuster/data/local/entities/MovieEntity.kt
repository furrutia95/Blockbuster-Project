package com.nerodev.blockbuster.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val adult: Boolean = false,
    val overview: String = "",
    val backdropPath: String = "",
    @ColumnInfo(name = "original_title")
    val originalTitle: String = "",
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val popularity: Double,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    )

