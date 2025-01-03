package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.*

class DeleteMoviesFromFavoritesUseCaseTest{
    private lateinit var useCase: DeleteMoviesFromFavoritesUseCase
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteMoviesFromFavoritesUseCase(repository)
    }

    @Test
    fun `invoke should call repository deleteMovieToFavorites`(): Unit = runBlocking{
        val movie = Movie(
            id = 1,
            title = "Example Task",
            adult = false,
            overview = "This is an example task",
            backdropUrl = "example_backdrop.jpg",
            originalTitle = "Example Title",
            releaseDate = "2023-06-01",
            popularity = 7.5,
            posterUrl = "example_poster.jpg"
        )
        useCase(movie)

        coVerify { repository.deleteMovieFromFavorites(movie) }
    }
}