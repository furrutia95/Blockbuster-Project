package com.nerodev.blockbuster.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.nerodev.blockbuster.presentation.uimodel.MovieUiModel


@Composable
fun MovieList(
    movies: List<MovieUiModel>,
    onMovieClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ){
        items(movies){ movie->
            MovieItem(
                movie = movie,
                onMovieClick = { onMovieClick(movie.id) }
            )
        }
    }
}