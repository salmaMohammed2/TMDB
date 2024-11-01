package com.example.mymovieapp.data.local

import com.example.mymovieapp.domain.entities.Movie

interface MoviesLocalDataSource {
    suspend fun getAllMovies(): List<Movie>

    suspend fun addMovie(movie: Movie)

    suspend fun deleteMovie(movieId: Int)

}