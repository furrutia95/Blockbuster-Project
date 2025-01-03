package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository

) {
    suspend operator fun invoke(id: Int): Movie {
        return repository.getMovieDetails(id)
    }

}