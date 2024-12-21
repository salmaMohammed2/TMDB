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
    override suspend fun getTopRatedMovies(pageNumber: Int): Response<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getTopRatedMovies(pageNumber)
        }
    }

    override suspend fun getPopularMovies(pageNumber: Int): Response<NowPlayingResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getPopularMovies(pageNumber)
        }
    }

    override suspend fun getNowPlayingMovies(pageNumber: Int): Response<NowPlayingResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getNowPlayingMovies(pageNumber)
        }
    }

    override suspend fun getMovie(movieName: String,pageNumber: Int): Response<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getMovie(movieName,pageNumber)
        }
    }

    override suspend fun getMovieGenre(): Response<GenresResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getMovieGenre()
        }
    }
}