package com.example.mymovieapp.domain.response

import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.model.Dates

data class NowPlayingResponse(
    val dates: Dates? = null,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)