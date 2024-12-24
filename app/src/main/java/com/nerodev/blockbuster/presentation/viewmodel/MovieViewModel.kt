package com.nerodev.blockbuster.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerodev.blockbuster.domain.usecase.AddFavoriteMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetFavoritesMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetPopularMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetSearchMoviesUseCase
import com.nerodev.blockbuster.presentation.mapper.MovieUiMapper
import com.nerodev.blockbuster.presentation.uimodel.MovieState
import com.nerodev.blockbuster.presentation.uimodel.MovieUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val addFavoriteMoviesUseCase: AddFavoriteMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase
) : ViewModel(){
    private val movieUiMapper: MovieUiMapper = MovieUiMapper()

    private val _uiState = MutableStateFlow(MovieState())
    val uiState: MutableStateFlow<MovieState> = _uiState

    init {
        loadMovies()
        loadFavorites()
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

    fun loadFavorites() = viewModelScope.launch {
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
                        favoritesMovies = uiModelFavorites,
                        isLoading = false
                    )
                }
            }
    }

    fun onUiEvent(event: MovieUiEvents){
        when(event){
            is MovieUiEvents.SearchMovies -> searchMovies(event.query)
            is MovieUiEvents.AddToFavorites -> addFavoriteMovies(event.movieId)
            is MovieUiEvents.FavoritesMovies -> _uiState.update {
                it.copy(favorites = event.currentFilter)
            }
            else -> TODO()
        }

    }

    fun addFavoriteMovies(movieId: Int) = viewModelScope.launch{
        val movie = _uiState.value.movies.find { it.id == movieId }
        if(movie != null)
            try{
                Log.d("MovieViewModel", "addFavoriteMovies: $movie")
                addFavoriteMoviesUseCase(movieUiMapper.toDomain(movie))
                _uiState.update { favoritesMovies ->
                    favoritesMovies.copy(
                        favoritesMovies = favoritesMovies.favoritesMovies + movie
                    )
                }
            }catch (e: Exception){
                Log.d("MovieViewModel", "addFavoriteMovies: ${e.message}")
            }
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        if(query.isNotEmpty()){
            getSearchMoviesUseCase(1, query)
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
        }else{
            loadMovies()
        }
    }

}