package com.nerodev.blockbuster.data.local.mapper

import com.nerodev.blockbuster.data.local.entities.MovieEntity
import com.nerodev.blockbuster.domain.model.Movie

class MovieMapperLocal {
    fun toDomain(entity: MovieEntity): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            overview = entity.overview,
            releaseDate = entity.releaseDate,
            popularity = entity.popularity,
            posterUrl = entity.posterUrl,
            originalTitle = entity.originalTitle,
            adult = entity.adult,
            backdropUrl = entity.backdropPath,
            isFavorite = true
        )
    }

    fun toEntity(domain: Movie): MovieEntity {
        return MovieEntity(
            id = domain.id,
            title = domain.title,
            overview = domain.overview,
            releaseDate = domain.releaseDate,
            popularity = domain.popularity,
            posterUrl = domain.posterUrl ?: "",
            originalTitle = domain.originalTitle,
            adult = domain.adult ?: false,
            backdropPath = domain.backdropUrl ?: "",
        )
    }

    fun toDomainList(entities: List<MovieEntity>): List<Movie> {
        return entities.map { toDomain(it) }
    }

    fun toEntityList(domains: List<Movie>): List<MovieEntity> {
        return domains.map { toEntity(it) }
    }
}