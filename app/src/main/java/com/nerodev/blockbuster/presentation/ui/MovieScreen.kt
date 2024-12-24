package com.nerodev.blockbuster.presentation.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nerodev.blockbuster.presentation.uimodel.MovieUiEvents
import com.nerodev.blockbuster.presentation.uimodel.MovieUiModel
import com.nerodev.blockbuster.presentation.viewmodel.MovieViewModel

@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    paddingValues: PaddingValues,
    onNavigate: (MovieUiEvents) -> Unit
){
    val uiState = viewModel.uiState.collectAsState()
    val movies = if (uiState.value.favorites){
        uiState.value.favoritesMovies
    }else{
        uiState.value.movies
    }


    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize())
    {
        when {
            uiState.value.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
            uiState.value.isError != "" -> {
                Text(
                    text = uiState.value.isError,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                Column {

                    SearchMovieScreen(
                        currentFilter = uiState.value.favorites,
                        onSearch = {
                            query ->
                            onNavigate(MovieUiEvents.SearchMovies(query))
                        },
                        onFilterChange = { currentFilter ->
                            onNavigate(MovieUiEvents.FavoritesMovies(currentFilter))
                        }
                    )
                    MovieList(
                        movies = movies,
                        onMovieClick = { movieId ->
                            onNavigate(MovieUiEvents.NavigateToDetail(movieId))
                        })
                }

            }
        }
    }
}


