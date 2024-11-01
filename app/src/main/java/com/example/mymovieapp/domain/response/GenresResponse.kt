package com.example.mymovieapp.domain.response

import com.example.mymovieapp.domain.model.Genre

data class GenresResponse(
    val genres: List<Genre>
)