package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.*

class GetFavoritesMoviesUseCaseTest{
    private lateinit var useCase: GetFavoritesMoviesUseCase
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetFavoritesMoviesUseCase(repository)
    }

    @Test
    fun `invoke should call repository getFavoriteMovies`(): Unit = runBlocking {
        val favoriteMovies = listOf(
            Movie(
                id = 1,
                title = "Example Task",
                adult = false,
                overview = "This is an example task",
                backdropUrl = "example_backdrop.jpg",
                originalTitle = "Example Title",
                releaseDate = "2023-06-01",
                popularity = 7.5,
                posterUrl = "example_poster.jpg",
                isFavorite = true
            ),
            Movie(
                id = 2,
                title = "Example Task 2",
                adult = false,
                overview = "This is an example task",
                backdropUrl = "example_backdrop.jpg",
                originalTitle = "Example Title",
                releaseDate = "2023-06-01",
                popularity = 7.5,
                posterUrl = "example_poster.jpg",
                isFavorite = true
            ),
            Movie(
                id = 3,
                title = "Example Task 3",
                adult = false,
                overview = "This is an example task",
                backdropUrl = "example_backdrop.jpg",
                originalTitle = "Example Title",
                releaseDate = "2023-06-01",
                popularity = 7.5,
                posterUrl = "example_poster.jpg",
                isFavorite = true
            )
        )

        coEvery { repository.getFavoriteMovies() } returns flowOf(favoriteMovies)

        val result = useCase()

        result.collect { movies ->
            assertEquals(favoriteMovies, movies)
        }
    }
}