package com.nerodev.blockbuster.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nerodev.blockbuster.presentation.ui.components.MovieList
import com.nerodev.blockbuster.presentation.uimodel.MovieUiEvents
import com.nerodev.blockbuster.ui.theme.Background
import com.nerodev.blockbuster.ui.theme.Primary
import com.nerodev.blockbuster.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(
    query: String,
    viewModel: SearchViewModel,
    paddingValues: PaddingValues,
    onNavigate: (MovieUiEvents) -> Unit,
    onBack: () -> Unit
) {
    viewModel.searchMovies(query)
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(56.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TextColor,
                    titleContentColor = Primary
                ),
                title = {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        color = Background,
                        fontSize = 24.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        text = "Your Search Results: $query"
                    )
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(TextColor)
        )
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top

        )

        {
            when {
                uiState.value.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }

                uiState.value.isError != "" -> {
                    uiState.value.isError?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> {
                    Column {
                        MovieList(
                            movies = uiState.value.movies,
                            onMovieClick = { movieId ->
                                onNavigate(MovieUiEvents.NavigateToDetail(movieId))
                            })
                    }

                }
            }
        }
    }
}