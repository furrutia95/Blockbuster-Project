package com.nerodev.blockbuster.presentation.uimodel

sealed class MovieUiEvents {
    data class SearchMovies(val query: String) : MovieUiEvents()
    data object AddToFavorites : MovieUiEvents()
    data object DeleteFromFavorites : MovieUiEvents()
    data class NavigateToDetail(val movieId: Int) : MovieUiEvents()
    data object NavigateToFavorites: MovieUiEvents()
    data class FavoritesMovies(val currentFilter: Boolean) : MovieUiEvents()
    data object NavigateToSearch: MovieUiEvents()
}