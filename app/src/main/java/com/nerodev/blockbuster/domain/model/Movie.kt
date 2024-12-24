package com.nerodev.blockbuster.domain.model

import com.squareup.moshi.Json

data class Movie(
    val adult: Boolean?,
    val backdropUrl: String?,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterUrl: String?,
    val releaseDate: String,
    val title: String,

)