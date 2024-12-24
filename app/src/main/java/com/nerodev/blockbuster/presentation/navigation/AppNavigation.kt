package com.nerodev.blockbuster.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nerodev.blockbuster.presentation.ui.MovieDetailScreen
import com.nerodev.blockbuster.presentation.ui.MovieScreen
import com.nerodev.blockbuster.presentation.uimodel.MovieUiEvents
import com.nerodev.blockbuster.presentation.viewmodel.MovieViewModel

@Composable
fun AppNavigation(paddingValues: PaddingValues){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.MovieList.route
    ){
        //MovieListScreen

        composable(ScreenNavigation.MovieList.route) {
            val viewModel = hiltViewModel<MovieViewModel>()
            MovieScreen(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onNavigate = { event ->
                    when(event){
                        is MovieUiEvents.NavigateToDetail -> {
                            navController.navigate(ScreenNavigation.MovieDetail.createRoute(event.movieId))
                        }
                        else -> {
                            viewModel.onUiEvent(event)
                        }
                    }

                }
            )
        }

        //MovieDetailScreen
        composable(
            route = ScreenNavigation.MovieDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ){  backStackEntry ->
            val viewModel = hiltViewModel<MovieViewModel>()
            val navId = backStackEntry.arguments?.getInt("id") ?: 0
            MovieDetailScreen(
                paddingValues,
                movieId = navId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onToggleFavorites = { movieId ->
                    viewModel.onUiEvent(MovieUiEvents.AddToFavorites(movieId))
                }
            )
        }


        }
    }
