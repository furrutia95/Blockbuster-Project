package com.nerodev.blockbuster.presentation.uimodel

data class MovieState (
    val movies: List<MovieUiModel> = emptyList(),
    val favoritesMovies: List<MovieUiModel> = emptyList(),
    val favorites: Boolean = false,
    val isLoading : Boolean = false,
    val isError : String = "",
)