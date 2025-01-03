package com.nerodev.blockbuster.data.local.mapper

import com.nerodev.blockbuster.data.local.entities.MovieEntity
import com.nerodev.blockbuster.domain.model.Movie
import junit.framework.TestCase.assertEquals
import org.junit.*

class MovieMapperLocalTest{
    private lateinit var mapper: MovieMapperLocal

    @Before
    fun setUp(){
        mapper = MovieMapperLocal()
    }

    @Test
    fun `toDomain maps MovieEntity to Movie correctly`(){
        val entity = MovieEntity(
            id = 1,
            title = "Example Task",
            adult = false,
            overview = "This is an example task",
            backdropPath = "example_backdrop.jpg",
            originalTitle = "Example Title",
            releaseDate = "2023-06-01",
            popularity = 7.5,
            posterUrl = "example_poster.jpg"
        )

        val domain = mapper.toDomain(entity)

        assertEquals(entity.id, domain.id)
        assertEquals(entity.title, domain.title)
    }

    @Test
    fun `toEntity maps Movie to MovieEntity correctly`(){
        val domain = Movie(
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
        )

        val entity = mapper.toEntity(domain)

        assertEquals(domain.id, entity.id)
        assertEquals(domain.title, entity.title)

    }
}