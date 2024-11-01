package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.domain.repo.MoviesRepo
import javax.inject.Inject

class GetMovieGenreUseCase @Inject constructor(private val moviesRepo: MoviesRepo) {
    suspend fun execute() = moviesRepo.getMovieGenre()
}