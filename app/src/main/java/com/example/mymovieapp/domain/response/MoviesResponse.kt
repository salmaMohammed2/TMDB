package com.example.mymovieapp.domain.response

import com.example.mymovieapp.domain.entities.Movie

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)