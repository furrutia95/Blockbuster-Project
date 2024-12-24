package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
){
    operator fun invoke(): Flow<List<Movie>> = repository.getFavoriteMovies()
}