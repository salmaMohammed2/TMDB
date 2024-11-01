package com.example.mymovieapp.domain.response

import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.model.Dates

data class NowPlayingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)