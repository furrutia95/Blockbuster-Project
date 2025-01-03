package com.nerodev.blockbuster.data.remote.mapper

import com.nerodev.blockbuster.data.remote.api.models.MovieDto
import junit.framework.TestCase.assertEquals
import org.junit.*

class MovieMapperRemoteTest{
    private lateinit var mapper: MovieMapperRemote

    @Before
    fun setUp(){
        mapper = MovieMapperRemote()
    }

    @Test
    fun `toDomain maps MovieDto to Movie correctly`() {
        val dto = MovieDto(
            id = 1,
            title = "Example Task",
            adult = false,
            overview = "This is an example task",
            backdropPath = "example_backdrop.jpg",
            originalTitle = "Example Title",
            releaseDate = "2023-06-01",
            popularity = 7.5,
            posterPath = "example_poster.jpg",
            video = false,
            voteAverage = 8.0,
            voteCount = 100,
            originalLanguage = "en",
            genreIds = listOf(1, 2, 3)
        )

        val domain = mapper.toDomain(dto)

        assertEquals(dto.id, domain.id)
        assertEquals(dto.title, domain.title)

    }


}