package com.nerodev.blockbuster.presentation.mapper

import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.presentation.uimodel.MovieUiModel

class MovieUiMapper {

    fun toUiModel(domain: Movie): MovieUiModel =
        MovieUiModel(
            id = domain.id,
            title = domain.title,
            overview = domain.overview,
            releaseDate = domain.releaseDate,
            popularity = domain.popularity,
            posterUrl = domain.posterUrl,
            originalTitle = domain.originalTitle,
            adult = domain.adult,
            backdropUrl = domain.backdropUrl,
            isFavorite = domain.isFavorite
        )

    fun toDomain(uiModel: MovieUiModel): Movie =
        Movie(
            id = uiModel.id,
            title = uiModel.title,
            overview = uiModel.overview,
            releaseDate = uiModel.releaseDate,
            popularity = uiModel.popularity ?: 0.0,
            posterUrl = uiModel.posterUrl,
            originalTitle = uiModel.originalTitle,
            adult = uiModel.adult,
            backdropUrl = uiModel.backdropUrl
        )

    fun toUiModelList(domains: List<Movie>): List<MovieUiModel> =
        domains.map { toUiModel(it) }


    fun toDomainList(uiModels: List<MovieUiModel>): List<Movie> =
        uiModels.map { toDomain(it) }

}