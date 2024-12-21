package com.example.mymovieapp.domain.enum

enum class MovieType(val type: Int) {
    NOW_PLAYING(0),
    TOP_RATED(1),
    POPULAR(2),
    FAVORITES(3),
    SEARCHED(4);
}