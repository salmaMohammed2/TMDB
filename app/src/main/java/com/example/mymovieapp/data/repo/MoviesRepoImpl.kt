package com.example.mymovieapp.data.repo

import com.example.mymovieapp.data.remote.MoviesRemoteDataSource
import com.example.mymovieapp.domain.repo.MoviesRepo
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) :
    MoviesRepo {
    override suspend fun getTopRatedMovies() = moviesRemoteDataSource.getTopRatedMovies()

    override suspend fun getPopularMovies() = moviesRemoteDataSource.getPopularMovies()

    override suspend fun getNowPlayingMovies() = moviesRemoteDataSource.getNowPlayingMovies()

    override suspend fun getMovie(movieName: String) = moviesRemoteDataSource.getMovie(movieName)

    override suspend fun getMovieGenre() = moviesRemoteDataSource.getMovieGenre()
}