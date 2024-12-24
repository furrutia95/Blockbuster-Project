package com.nerodev.blockbuster.data.remote.mapper

import com.nerodev.blockbuster.data.remote.api.models.MovieDto
import com.nerodev.blockbuster.domain.model.Movie

class MovieMapperRemote {

    fun toDomain(dto: MovieDto): Movie =
        Movie(
            id = dto.id,
            adult = dto.adult,
            backdropUrl = dto.backdropPath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            },
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            popularity = dto.popularity,
            posterUrl = dto.posterPath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            },
            releaseDate = dto.releaseDate,
            title = dto.title,
        )

    fun toDomainList(dtos: List<MovieDto>): List<Movie> =
        dtos.map { toDomain(it) }



}