package com.example.mymovieapp.data.repo

import com.example.mymovieapp.data.local.MoviesLocalDataSource
import com.example.mymovieapp.data.remote.MoviesRemoteDataSource
import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.repo.DatabaseRepo
import com.example.mymovieapp.domain.repo.MoviesRepo
import com.example.mymovieapp.domain.response.MoviesResponse
import retrofit2.Response
import javax.inject.Inject

class DatabaseRepoImpl @Inject constructor(private val moviesLocalDataSource: MoviesLocalDataSource) :
    DatabaseRepo {
    override suspend fun getAllMovies(): List<Movie> {
        return moviesLocalDataSource.getAllMovies()
    }

    override suspend fun addMovie(movie: Movie) {
        moviesLocalDataSource.addMovie(movie)
    }

    override suspend fun deleteMovie(movieId: Int) {
        moviesLocalDataSource.deleteMovie(movieId)
    }

}