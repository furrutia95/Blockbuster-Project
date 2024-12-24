package com.nerodev.blockbuster.di

import android.content.Context
import androidx.room.Room
import com.nerodev.blockbuster.data.local.datasource.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context = context,
            MovieDatabase::class.java,
            "favorite_movies"
        ).build()
    }

    @Provides
    fun provideMovieDao(movieDataBase: MovieDatabase) = movieDataBase.movieDao()
}