package com.nerodev.blockbuster.presentation.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.blockbuster.domain.usecase.AddFavoriteMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.DeleteMoviesFromFavoritesUseCase
import com.nerodev.blockbuster.domain.usecase.GetMovieDetailsUseCase
import com.nerodev.blockbuster.presentation.mapper.MovieUiMapper
import com.nerodev.blockbuster.presentation.uimodel.MovieState
import com.nerodev.blockbuster.presentation.uimodel.MovieUiEvents
import com.nerodev.blockbuster.presentation.uimodel.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addFavoriteMoviesUseCase: AddFavoriteMoviesUseCase,
    private val deleteMoviesFromFavoritesUseCase: DeleteMoviesFromFavoritesUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {
    private val movieUiMapper: MovieUiMapper = MovieUiMapper()

    private val _uiState = MutableStateFlow(MovieState())
    val uiState: StateFlow<MovieState> = _uiState.asStateFlow()

    private val _movieDetail = MutableStateFlow<MovieUiModel?>(null)
    val movieDetail: StateFlow<MovieUiModel?> = _movieDetail.asStateFlow()

    fun loadMovieDetails(movieId: Int) = viewModelScope.launch {
        flow {
            emit(getMovieDetailsUseCase(movieId))
        }
            .onStart {
                _uiState.update { it.copy(isLoading = true) }
            }
            .catch { error ->
                _uiState.update { it.copy(isError = error.message.toString()) }
            }
            .collect { domainMovie ->
                _movieDetail.value = movieUiMapper.toUiModel(domainMovie)
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
    }

    fun onUiEvent(event: MovieUiEvents) {
        when (event) {
            is MovieUiEvents.AddToFavorites -> addFavoriteMovies()
            else -> deleteFavoriteMovies()
        }

    }

    fun addFavoriteMovies() = viewModelScope.launch {
        val movie = _movieDetail.value
        if (movie != null)
            try {
                addFavoriteMoviesUseCase(movieUiMapper.toDomain(movie))
                _movieDetail.value = _movieDetail.value?.copy(isFavorite = true)

            } catch (e: Exception) {
                Log.d("DetailViewModel", "addFavoriteMovies: ${e.message}")
            }
    }

    fun deleteFavoriteMovies() = viewModelScope.launch {
        val movie = _movieDetail.value
        if (movie != null) {
            try {
                deleteMoviesFromFavoritesUseCase(movieUiMapper.toDomain(movie))
                _movieDetail.value = _movieDetail.value?.copy(isFavorite = false)
            } catch (e: Exception) {
                Log.d("DetailViewModel", "deleteFavoriteMovies: ${e.message}")
            }
        }
    }
}
