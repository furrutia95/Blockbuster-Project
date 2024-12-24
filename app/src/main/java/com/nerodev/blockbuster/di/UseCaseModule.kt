package com.nerodev.blockbuster.di

import com.nerodev.blockbuster.domain.repository.MovieRepository
import com.nerodev.blockbuster.domain.usecase.AddFavoriteMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetFavoritesMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetPopularMoviesUseCase
import com.nerodev.blockbuster.domain.usecase.GetSearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetPopularMoviesUseCase(repository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }

    @Provides
    fun provideGetSearchMoviesUseCase(repository: MovieRepository): GetSearchMoviesUseCase {
        return GetSearchMoviesUseCase(repository)
    }

    @Provides
    fun provideAddFavoriteMoviesUseCase(repository: MovieRepository): AddFavoriteMoviesUseCase {
        return AddFavoriteMoviesUseCase(repository)
    }

    @Provides
    fun provideGetFavoritesMoviesUseCase(repository: MovieRepository): GetFavoritesMoviesUseCase {
        return GetFavoritesMoviesUseCase(repository)
    }

}