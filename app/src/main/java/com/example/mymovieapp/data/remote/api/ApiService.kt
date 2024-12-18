package com.example.mymovieapp.data.remote.api

import com.example.mymovieapp.data.remote.Endpoints.TOP_RATED_MOVIES
import com.example.mymovieapp.data.remote.Endpoints.NOW_PLAYING_MOVIES
import com.example.mymovieapp.data.remote.Endpoints.GENRE_MOVIES
import com.example.mymovieapp.data.remote.Endpoints.POPULAR_MOVIES
import com.example.mymovieapp.data.remote.Endpoints.SEARCH_MOVIE
import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.response.NowPlayingResponse
import com.example.mymovieapp.domain.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(@Query("page") pageNumber: Int): Response<MoviesResponse>

    @GET(POPULAR_MOVIES)
    suspend fun getPopularMovies(@Query("page") pageNumber: Int): Response<NowPlayingResponse>

    @GET(NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(@Query("page") pageNumber: Int): Response<NowPlayingResponse>

    @GET(SEARCH_MOVIE)
    suspend fun getMovie(@Query("query") movieName: String): Response<MoviesResponse>

    @GET(GENRE_MOVIES)
    suspend fun getMovieGenre(): Response<GenresResponse>
}