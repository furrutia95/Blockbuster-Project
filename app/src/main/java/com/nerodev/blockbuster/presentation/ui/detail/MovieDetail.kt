package com.nerodev.blockbuster.presentation.ui.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nerodev.blockbuster.ui.theme.Background
import com.nerodev.blockbuster.ui.theme.Tertiary
import com.nerodev.blockbuster.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    paddingValues: PaddingValues,
    movieId: Int,
    viewModel: DetailViewModel,
    onBack: () -> Unit,
    onToggleFavorites: () -> Unit,
    onDeleteFavorites: () -> Unit
) {
    viewModel.loadMovieDetails(movieId)
    val movieState by viewModel.movieDetail.collectAsState()

    Log.d("MovieDetailScreen", "Movie ID: $movieState")

    if (movieState != null) {
        val movie = movieState!!
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Fondo con la imagen del backdrop
            AsyncImage(
                model = movie.backdropUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Asegura que la imagen se ajuste al tamaño
            )

            // Overlay oscuro para mejorar la visibilidad del texto
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            // Contenido principal
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Transparent), // Fondo transparente para mostrar el backdrop
                topBar = {
                    TopAppBar(
                        title = { Text(movie.title) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = TextColor,
                            titleContentColor = Background
                        ),
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Background
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    AsyncImage(
                        model = movie.backdropUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // Ajuste al tamaño del Box
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(innerPadding),
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier.padding(16.dp),
                        model = movie.posterUrl,
                        contentDescription = "Poster"
                    )
                    Row {
                        Text(
                            "Release Date: ${movie.releaseDate}",
                            color = Background
                        )
                    }
                    Button(
                        onClick = { if (movie.isFavorite) onDeleteFavorites() else onToggleFavorites() },
                        colors = if (movie.isFavorite) {
                            ButtonDefaults.buttonColors(containerColor = Color.Red)
                        } else {
                            ButtonDefaults.buttonColors(containerColor = Tertiary)
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Favorite,
                            contentDescription = "Favorites",
                            tint = if (movie.isFavorite) Background else TextColor
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            "Favorites",
                            color = if (movie.isFavorite) Background else TextColor
                        )
                    }
                    Text(
                        movie.overview,
                        color = Background,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
    } else {
        Text(
            "Movie not found",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}
