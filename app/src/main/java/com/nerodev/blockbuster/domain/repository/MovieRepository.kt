package com.nerodev.blockbuster.domain.repository

import com.nerodev.blockbuster.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(page: Int): Flow<List<Movie>>
    suspend fun getSearchMovies(page: Int, query: String): Flow<List<Movie>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun addMovieToFavorites(movie: Movie)
    suspend fun deleteMovieFromFavorites(movie: Movie)
    suspend fun getMovieDetails(id: Int): Movie
}