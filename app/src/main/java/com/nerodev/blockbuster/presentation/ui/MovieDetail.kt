package com.nerodev.blockbuster.presentation.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.nerodev.blockbuster.presentation.viewmodel.MovieViewModel
import com.nerodev.blockbuster.ui.theme.Background
import com.nerodev.blockbuster.ui.theme.Secondary
import com.nerodev.blockbuster.ui.theme.Tertiary
import com.nerodev.blockbuster.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    paddingValues: PaddingValues,
    movieId: Int,
    viewModel: MovieViewModel,
    onBack: () -> Unit,
    onToggleFavorites: (Int)-> Unit,
){
    val uiState = viewModel.uiState.collectAsState()
    val movie = uiState.value.movies.find { it.id == movieId }


    if(movie == null){
        Text("Movie not found",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            textAlign = TextAlign.Center)
        return
    }

    var enabled:Boolean = true
    if(uiState.value.favoritesMovies.contains(movie)){
        enabled = false
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize().padding(paddingValues),
        topBar = {
            TopAppBar(
                title = { Text(movie.title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TextColor,
                    titleContentColor = Background
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = "",
            )
            Row(modifier = Modifier
                .background(Secondary)) {
                Text(movie.releaseDate)
                Text(movie.originalTitle)
            }
            Button(
                onClick = { onToggleFavorites(movie.id) },
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(containerColor = Secondary) ) {
                Icon(
                    Icons.Rounded.Favorite,
                    contentDescription = "Favorites"
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text("Favorites")
            }
            Text(movie.overview,
                modifier = Modifier.padding(16.dp).background(Tertiary))

        }


    }





}