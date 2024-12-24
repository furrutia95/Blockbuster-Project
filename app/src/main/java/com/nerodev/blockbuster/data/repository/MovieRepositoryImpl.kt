package com.nerodev.blockbuster.data.repository

import android.util.Log
import androidx.room.Room
import com.nerodev.blockbuster.data.local.dao.MovieDao
import com.nerodev.blockbuster.data.local.datasource.MovieDatabase
import com.nerodev.blockbuster.data.local.mapper.MovieMapperLocal
import com.nerodev.blockbuster.data.remote.client.RetroFitClient
import com.nerodev.blockbuster.data.remote.mapper.MovieMapperRemote
import com.nerodev.blockbuster.domain.model.Movie
import com.nerodev.blockbuster.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val movieDao: MovieDao
): MovieRepository
{
    private val api = RetroFitClient.movieApi
    private val mapperRemote = MovieMapperRemote()
    private val mapperLocal = MovieMapperLocal()
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun getPopularMovies(page: Int): Flow<List<Movie>> = flow {
        val response = api.getPopularMovies(page = page)
        emit(response.movies.map { mapperRemote.toDomain(it) })
    }.catch { e ->
        throw e
    }.flowOn(dispatcher)

    override suspend fun getSearchMovies(page: Int, query: String): Flow<List<Movie>> = flow {
        val response = api.getSearchMovies(page = 1, query = query)
        emit(response.movies.map { mapperRemote.toDomain(it) })
    }.catch { e ->
        throw e
    }.flowOn(dispatcher)

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { entities ->
            mapperLocal.toDomainList(entities)
        }.catch { exception ->
            Log.e("MovieRepositoryImplLocal", exception.message.toString())
            emit(emptyList())
        }
    }

    override suspend fun addMovieToFavorites(movie: Movie) {
        try {
            movieDao.insertMovie(mapperLocal.toEntity(movie))
        }catch (e:Exception){
            Log.e("MovieRepositoryImplLocal", e.message.toString())
        }
    }


}