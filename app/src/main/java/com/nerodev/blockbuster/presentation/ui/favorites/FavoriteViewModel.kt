package com.nerodev.blockbuster.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.blockbuster.domain.usecase.GetFavoritesMoviesUseCase
import com.nerodev.blockbuster.presentation.mapper.MovieUiMapper
import com.nerodev.blockbuster.presentation.uimodel.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase
) : ViewModel() {
    private val movieUiMapper: MovieUiMapper = MovieUiMapper()

    private val _uiState = MutableStateFlow(MovieState())
    val uiState: MutableStateFlow<MovieState> = _uiState

    init {
        loadFavorites()
    }

    private fun loadFavorites() = viewModelScope.launch {
        getFavoritesMoviesUseCase()
            .onStart {
                _uiState.update { it.copy(isLoading = true) }
            }
            .catch { error ->
                _uiState.update { it.copy(isError = error.message.toString()) }
            }.map { domainFavorites ->
                movieUiMapper.toUiModelList(domainFavorites)
            }.collect { uiModelFavorites ->
                _uiState.update {
                    it.copy(
                        movies = uiModelFavorites,
                        isLoading = false
                    )
                }
            }
    }
}