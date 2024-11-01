package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.domain.repo.DatabaseRepo
import javax.inject.Inject

class GetAllMoviesFromDatabaseUseCase @Inject constructor(private val databaseRepo: DatabaseRepo) {
    suspend fun execute() = databaseRepo.getAllMovies()
}