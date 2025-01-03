package com.nerodev.blockbuster.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nerodev.blockbuster.presentation.ui.components.SearchMovieScreen
import com.nerodev.blockbuster.presentation.ui.detail.DetailViewModel
import com.nerodev.blockbuster.presentation.ui.detail.MovieDetailScreen
import com.nerodev.blockbuster.presentation.ui.favorites.FavoriteViewModel
import com.nerodev.blockbuster.presentation.ui.favorites.MovieFavoritesScreen
import com.nerodev.blockbuster.presentation.ui.main.MovieScreen
import com.nerodev.blockbuster.presentation.ui.main.MovieViewModel
import com.nerodev.blockbuster.presentation.ui.search.MovieSearchScreen
import com.nerodev.blockbuster.presentation.ui.search.SearchViewModel
import com.nerodev.blockbuster.presentation.uimodel.MovieUiEvents
import com.nerodev.blockbuster.ui.theme.Secondary
import com.nerodev.blockbuster.ui.theme.Tertiary
import com.nerodev.blockbuster.ui.theme.TextColor

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavigation(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MovieViewModel>()
    val items = listOf(
        ScreenNavigation.MovieList,
        ScreenNavigation.FavoritesMovies
    )
    val selectedIcons = listOf(
        Icons.Filled.Star,
        Icons.Filled.Favorite
    )
    val openAlertDialog = remember { mutableStateOf(false) }


    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openAlertDialog.value = true
                },
                containerColor = Color.Black,
                contentColor = Secondary) {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "Search"
                )
            }

        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                selectedIcons[index],
                                contentDescription = screen.route,
                                tint = Tertiary
                            )
                        },
                        label = {
                            Text(
                                screen.route,
                                color = Tertiary
                            )
                        },
                        selected = currentDestination?.route == screen.route,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Gray.copy(alpha = 0.5f)
                        ),
                        onClick = {
                            navController.navigate(screen.route) {
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenNavigation.MovieList.route,
            Modifier.padding(innerPadding)
        ) {
            //MovieListScreen
            composable(ScreenNavigation.MovieList.route) {
                MovieScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    onNavigate = { event ->
                        when (event) {
                            is MovieUiEvents.NavigateToDetail -> {
                                navController.navigate(
                                    ScreenNavigation.MovieDetail.createRoute(
                                        event.movieId
                                    )
                                )
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
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val navId = backStackEntry.arguments?.getInt("id") ?: 0
                val detailViewModel = hiltViewModel<DetailViewModel>()
                MovieDetailScreen(
                    paddingValues,
                    movieId = navId,
                    viewModel = detailViewModel,
                    onBack = { navController.popBackStack() },
                    onToggleFavorites = {
                        detailViewModel.onUiEvent(event = MovieUiEvents.AddToFavorites)
                    },
                    onDeleteFavorites = {
                        detailViewModel.onUiEvent(event = MovieUiEvents.DeleteFromFavorites)
                    }
                )
            }

            //MovieFavoritesScreen
            composable(
                route = ScreenNavigation.FavoritesMovies.route
            ) {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                MovieFavoritesScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    onNavigate = { event ->
                        (event as? MovieUiEvents.NavigateToDetail)?.let {
                            navController.navigate(ScreenNavigation.MovieDetail.createRoute(it.movieId))
                        }
                    },
                )
            }

            //MovieSearchScreen
            composable(
                route = ScreenNavigation.SearchMovies.route,
                arguments = listOf(navArgument("query") { type = NavType.StringType })

            ) { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                val viewModel = hiltViewModel<SearchViewModel>()
                MovieSearchScreen(
                    query = query,
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    onNavigate = { event ->
                        (event as? MovieUiEvents.NavigateToDetail)?.let {
                            navController.navigate(ScreenNavigation.MovieDetail.createRoute(it.movieId))
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }


        }

        if (openAlertDialog.value) {
            SearchMovieScreen(
                onDismissRequest = { openAlertDialog.value = false },
                onSearch = { query ->
                    navController.navigate(ScreenNavigation.SearchMovies.createRoute(query))
                    openAlertDialog.value = false
                }
            )
        }
    }

}


