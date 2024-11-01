package com.example.mymovieapp.data.remote

import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.response.NowPlayingResponse
import com.example.mymovieapp.domain.response.MoviesResponse
import retrofit2.Response

interface MoviesRemoteDataSource {
    suspend fun getTopRatedMovies(): Response<MoviesResponse>
    suspend fun getPopularMovies(): Response<NowPlayingResponse>
    suspend fun getNowPlayingMovies(): Response<NowPlayingResponse>
    suspend fun getMovie(movieName: String): Response<MoviesResponse>
    suspend fun getMovieGenre(): Response<GenresResponse>
}