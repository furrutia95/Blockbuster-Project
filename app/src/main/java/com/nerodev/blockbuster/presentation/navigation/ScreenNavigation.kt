package com.nerodev.blockbuster.presentation.navigation

sealed class ScreenNavigation(val route:String) {

    data object MovieList: ScreenNavigation("movies")
    data object MovieDetail: ScreenNavigation("movie/{id}") {
        fun createRoute(id: Int) = "movie/$id"
    }
    data object FavoritesMovies: ScreenNavigation("favorites")
    data object SearchMovies: ScreenNavigation("search/{query}"){
        fun createRoute(query: String) = "search/$query"
    }

}