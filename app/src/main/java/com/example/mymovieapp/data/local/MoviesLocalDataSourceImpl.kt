package com.example.mymovieapp.data.local

import com.example.mymovieapp.data.local.database.MoviesDao
import com.example.mymovieapp.domain.entities.Movie

class MoviesLocalDataSourceImpl(private val moviesDao: MoviesDao) : MoviesLocalDataSource {
    override suspend fun getAllMovies(): List<Movie> {
        return moviesDao.getAllMovies()
    }

    override suspend fun addMovie(movie: Movie) {
        moviesDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movieId: Int) {
        moviesDao.deleteMovie(movieId)
    }
}