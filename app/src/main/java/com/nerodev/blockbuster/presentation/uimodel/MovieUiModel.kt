package com.nerodev.blockbuster.presentation.uimodel

data class MovieUiModel (
    val adult: Boolean?,
    val backdropUrl: String?,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double?,
    val posterUrl: String?,
    val releaseDate: String,
    val title: String,
    val isFavorite: Boolean
)