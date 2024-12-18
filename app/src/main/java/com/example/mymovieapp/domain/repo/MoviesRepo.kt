package com.example.mymovieapp.domain.repo

import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.response.MoviesResponse
import com.example.mymovieapp.domain.response.NowPlayingResponse
import retrofit2.Response

interface MoviesRepo {
    suspend fun getTopRatedMovies(pageNumber: Int): Response<MoviesResponse>
    suspend fun getPopularMovies(pageNumber: Int): Response<NowPlayingResponse>
    suspend fun getNowPlayingMovies(pageNumber: Int): Response<NowPlayingResponse>
    suspend fun getMovie(movieName: String): Response<MoviesResponse>
    suspend fun getMovieGenre(): Response<GenresResponse>
}