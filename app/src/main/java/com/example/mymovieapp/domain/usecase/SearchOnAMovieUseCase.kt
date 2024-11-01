package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.domain.repo.MoviesRepo
import javax.inject.Inject

class SearchOnAMovieUseCase @Inject constructor(private val moviesRepo: MoviesRepo) {
    suspend fun execute(movieName: String) = moviesRepo.getMovie(movieName)
}