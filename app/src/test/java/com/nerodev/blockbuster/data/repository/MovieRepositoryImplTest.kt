package com.nerodev.blockbuster.data.repository

import com.nerodev.blockbuster.data.local.dao.MovieDao
import com.nerodev.blockbuster.data.local.entities.MovieEntity
import com.nerodev.blockbuster.data.local.mapper.MovieMapperLocal
import com.nerodev.blockbuster.domain.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    private lateinit var mapper: MovieMapperLocal
    private lateinit var movieRepository:MovieRepositoryImpl
    @MockK
    private lateinit var movieDao: MovieDao

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        movieRepository = MovieRepositoryImpl(movieDao)
        mapper = MovieMapperLocal()
    }


    @Test
    fun `addMovieToFavorites should call movieDao insertMovie`(): Unit = runBlocking{
        val domainMovie = Movie(
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
        val entityMovie = mapper.toEntity(domainMovie)
        coEvery { movieDao.insertMovie(entityMovie) } just Runs

        movieRepository.addMovieToFavorites(domainMovie)

        coVerify { movieDao.insertMovie(entityMovie) }

    }


}