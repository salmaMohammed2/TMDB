package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.repo.DatabaseRepo
import javax.inject.Inject

class AddMovieToDatabaseUseCase @Inject constructor(private val databaseRepo: DatabaseRepo) {
    suspend fun execute(movie: Movie) = databaseRepo.addMovie(movie)
}