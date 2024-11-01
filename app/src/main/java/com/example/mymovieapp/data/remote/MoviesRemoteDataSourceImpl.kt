package com.example.mymovieapp.data.remote

import com.example.mymovieapp.data.remote.api.RetrofitInstance
import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.response.MoviesResponse
import com.example.mymovieapp.domain.response.NowPlayingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor() : MoviesRemoteDataSource {
    override suspend fun getTopRatedMovies(): Response<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getTopRatedMovies()
        }
    }

    override suspend fun getPopularMovies(): Response<NowPlayingResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getPopularMovies()
        }
    }

    override suspend fun getNowPlayingMovies(): Response<NowPlayingResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getNowPlayingMovies()
        }
    }

    override suspend fun getMovie(movieName: String): Response<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getMovie(movieName)
        }
    }

    override suspend fun getMovieGenre(): Response<GenresResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getMovieGenre()
        }
    }
}