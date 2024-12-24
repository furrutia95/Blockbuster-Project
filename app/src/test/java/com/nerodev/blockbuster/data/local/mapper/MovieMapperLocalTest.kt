package com.nerodev.blockbuster.data.local.mapper

import com.nerodev.blockbuster.data.local.entities.MovieEntity
import junit.framework.TestCase.assertEquals
import org.junit.*

class MovieMapperLocalTest{
    private lateinit var mapper: MovieMapperLocal

    @Before
    fun setUp(){
        mapper = MovieMapperLocal()
    }

    @Test
    fun `toDomain maps TaskEntity to Task correctly`(){
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
    fun `toEntity maps Task to TaskEntity correctly`(){
        // TODO: Implement test
    }
}