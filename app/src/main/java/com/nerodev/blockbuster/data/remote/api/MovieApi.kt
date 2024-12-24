package com.nerodev.blockbuster.data.remote.api

import com.nerodev.blockbuster.data.remote.api.models.MovieDto
import com.nerodev.blockbuster.data.remote.api.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
    ): MovieResponse

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ): MovieDto

}