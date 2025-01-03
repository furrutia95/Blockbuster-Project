package com.nerodev.blockbuster.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nerodev.blockbuster.presentation.uimodel.MovieUiModel

@Composable
fun MovieItem(
    movie: MovieUiModel,
    onMovieClick: (Int) -> Unit) {
    Card(
        modifier = Modifier.clickable{
            onMovieClick(movie.id)
        }
    ){
        OutlinedCard(
            border = BorderStroke(1.dp, androidx.compose.ui.graphics.Color.Black)
        ) {

            AsyncImage(
                model = movie.posterUrl,
                contentDescription = ""
            )
        }
    }
}