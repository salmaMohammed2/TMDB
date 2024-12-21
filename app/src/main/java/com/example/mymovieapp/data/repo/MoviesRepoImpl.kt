package com.example.mymovieapp.data.repo

import com.example.mymovieapp.data.remote.MoviesRemoteDataSource
import com.example.mymovieapp.domain.repo.MoviesRepo
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) :
    MoviesRepo {
    override suspend fun getTopRatedMovies(pageNumber: Int) =
        moviesRemoteDataSource.getTopRatedMovies(pageNumber)

    override suspend fun getPopularMovies(pageNumber: Int) =
        moviesRemoteDataSource.getPopularMovies(pageNumber)

    override suspend fun getNowPlayingMovies(pageNumber: Int) =
        moviesRemoteDataSource.getNowPlayingMovies(pageNumber)

    override suspend fun getMovie(movieName: String, pageNumber: Int) =
        moviesRemoteDataSource.getMovie(movieName, pageNumber)

    override suspend fun getMovieGenre() = moviesRemoteDataSource.getMovieGenre()
}