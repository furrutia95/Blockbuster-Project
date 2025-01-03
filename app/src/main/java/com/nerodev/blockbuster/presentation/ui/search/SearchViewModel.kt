package com.nerodev.blockbuster.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.blockbuster.domain.usecase.GetSearchMoviesUseCase
import com.nerodev.blockbuster.presentation.mapper.MovieUiMapper
import com.nerodev.blockbuster.presentation.uimodel.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
) : ViewModel() {
    private val movieUiMapper: MovieUiMapper = MovieUiMapper()

    private val _uiState = MutableStateFlow(MovieState())
    val uiState: StateFlow<MovieState> = _uiState.asStateFlow()

    fun searchMovies(query: String) = viewModelScope.launch {
        getSearchMoviesUseCase(1, query)
            .onStart {
                _uiState.update { it.copy(isLoading = true) }
            }.catch { error ->
                _uiState.update { it.copy(isError = error.message.toString()) }
            }.map { domainMovies ->
                movieUiMapper.toUiModelList(domainMovies)
            }.collectLatest { uiModelMovies ->
                _uiState.update {
                    it.copy(
                        movies = uiModelMovies,
                        isLoading = false,
                    )
                }
            }
    }
}