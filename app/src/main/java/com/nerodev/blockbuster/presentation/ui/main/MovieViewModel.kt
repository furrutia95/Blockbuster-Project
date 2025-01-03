package com.nerodev.blockbuster.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.blockbuster.domain.usecase.AddFavoriteMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.DeleteMoviesFromFavoritesUseCase
import com.nerodev.blockbuster.domain.usecase.GetFavoritesMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetPopularMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetSearchMoviesUseCase
import com.nerodev.blockbuster.presentation.mapper.MovieUiMapper
import com.nerodev.blockbuster.presentation.uimodel.MovieState
import com.nerodev.blockbuster.presentation.uimodel.MovieUiEvents
import com.nerodev.blockbuster.presentation.uimodel.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
) : ViewModel(){
    private val movieUiMapper: MovieUiMapper = MovieUiMapper()

    private val _uiState = MutableStateFlow(MovieState())
    val uiState : StateFlow<MovieState> = _uiState.asStateFlow()


    init {
        loadMovies()
    }

    fun loadMovies() = viewModelScope.launch {
        getPopularMoviesUseCase(1)
            .onStart {
                _uiState.update { it.copy(isLoading = true) }

            }.catch { error ->
                _uiState.update { it.copy(isError = error.message.toString()) }
            }.map { domainMovies ->
                movieUiMapper.toUiModelList(domainMovies)
            }.collect{ uiModelMovies ->
                _uiState.update {
                    it.copy(
                        movies = uiModelMovies,
                        isLoading = false
                    )
                }
            }
        Log.d("MovieViewModel", "loadMovies: ${_uiState.value.movies}")
    }


    fun onUiEvent(event: MovieUiEvents){
        when(event){
            is MovieUiEvents.FavoritesMovies -> _uiState.update {
                it.copy(favorites = event.currentFilter)
            }
            else -> TODO()
        }

    }


    fun getMovieDetail(movieId: Int): MovieUiModel? {
        Log.d("MovieDetailScreen", "getMovieDetail: ${uiState.value.movies}")
        return uiState.value.movies.find { it.id == movieId }
    }
}