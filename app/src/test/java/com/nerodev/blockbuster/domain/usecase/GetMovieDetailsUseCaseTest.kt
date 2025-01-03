package com.nerodev.blockbuster.domain.usecase

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.*

class GetMovieDetailsUseCaseTest{
    private lateinit var useCase: GetMovieDetailsUseCase
    private lateinit var repository: MovieRepository


    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `invoke should call repository getMovieDetails`(): Unit = runBlocking {
        val movieId = 1
        val movie = Movie(
            id = movieId,
            title = "Example Task",
            adult = false,
            overview = "This is an example task",
            backdropUrl = "example_backdrop.jpg",
            originalTitle = "Example Title",
            releaseDate = "2023-06-01",
            popularity = 7.5,
            posterUrl = "example_poster.jpg"
        )

        coEvery { repository.getMovieDetails(movieId) } returns movie

        val result = useCase(movieId)

        assertEquals(movie, result)

    }
}