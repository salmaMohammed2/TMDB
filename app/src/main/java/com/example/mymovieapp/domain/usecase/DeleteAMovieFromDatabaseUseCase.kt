package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.domain.repo.DatabaseRepo
import javax.inject.Inject

class DeleteAMovieFromDatabaseUseCase @Inject constructor(private val databaseRepo: DatabaseRepo) {
    suspend fun execute(movieId: Int) = databaseRepo.deleteMovie(movieId)
}