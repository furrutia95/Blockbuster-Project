package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository

) {
    suspend operator fun invoke(page: Int, query: String): Flow<List<Movie>> {
        return repository.getSearchMovies(page, query)
    }
}