package com.nerodev.blockbuster.presentation.navigation

sealed class ScreenNavigation(val route:String) {

    data object MovieList: ScreenNavigation("movies")
    data object MovieDetail: ScreenNavigation("movie/{id}") {
        fun createRoute(id: Int) = "movie/$id"
    }
    data object FavoritesMovies: ScreenNavigation("favorites")

}