package com.example.mymovieapp.data.remote

import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.response.MoviesResponse
import com.example.mymovieapp.domain.response.NowPlayingResponse
import retrofit2.Response

interface MoviesRemoteDataSource {
    suspend fun getTopRatedMovies(pageNumber: Int): Response<MoviesResponse>
    suspend fun getPopularMovies(pageNumber: Int): Response<NowPlayingResponse>
    suspend fun getNowPlayingMovies(pageNumber: Int): Response<NowPlayingResponse>
    suspend fun getMovie(movieName: String, pageNumber: Int): Response<MoviesResponse>
    suspend fun getMovieGenre(): Response<GenresResponse>
}