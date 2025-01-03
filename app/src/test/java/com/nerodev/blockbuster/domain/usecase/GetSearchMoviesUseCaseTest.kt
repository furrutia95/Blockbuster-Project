package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.*

class GetSearchMoviesUseCaseTest{
    private lateinit var useCase: GetSearchMoviesUseCase
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetSearchMoviesUseCase(repository)
    }

    @Test
    fun `invoke should call repository getSearchMovies`(): Unit = runBlocking {
        val page = 1
        val query = "Example Query"
        val movies = listOf(
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
                isFavorite = false
            )
        )

        coEvery { repository.getSearchMovies(page, query) } returns flowOf(movies)

        val result = useCase(page, query)

        result.collect { moviesList ->
            assertEquals(movies, moviesList)
        }
    }
}