package com.example.mymovieapp.domain.repo

import com.example.mymovieapp.domain.entities.Movie

interface DatabaseRepo {
    suspend fun getAllMovies(): List<Movie>

    suspend fun addMovie(movie: Movie)

    suspend fun deleteMovie(movieId: Int)
}