package com.nerodev.blockbuster.presentation.uimodel

sealed class MovieUiEvents {
    data class SearchMovies(val query: String) : MovieUiEvents()
    data class AddToFavorites(val movieId: Int) : MovieUiEvents()
    data class NavigateToDetail(val movieId: Int) : MovieUiEvents()
    data class FavoritesMovies(val currentFilter: Boolean) : MovieUiEvents()
}