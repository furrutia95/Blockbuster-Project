package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMoviesFromFavoritesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.deleteMovieFromFavorites(movie)
    }
}