package com.example.mymovieapp.domain.repo

import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.response.NowPlayingResponse
import com.example.mymovieapp.domain.response.MoviesResponse
import retrofit2.Response

interface DatabaseRepo {
    suspend fun getAllMovies(): List<Movie>

    suspend fun addMovie(movie: Movie)

    suspend fun deleteMovie(movieId: Int)
}