package com.example.mymovieapp.presentation.viewModel

import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.response.NowPlayingResponse
import com.example.mymovieapp.domain.usecase.AddMovieToDatabaseUseCase
import com.example.mymovieapp.domain.usecase.DeleteAMovieFromDatabaseUseCase
import com.example.mymovieapp.domain.usecase.GetAllMoviesFromDatabaseUseCase
import com.example.mymovieapp.domain.usecase.GetMovieGenreUseCase
import com.example.mymovieapp.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.mymovieapp.domain.usecase.GetPopularMoviesUseCase
import com.example.mymovieapp.domain.usecase.GetTopRatedMoviesUseCase
import com.example.mymovieapp.domain.usecase.SearchOnAMovieUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MoviesViewModelTest {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var searchOnAMovieUseCase: SearchOnAMovieUseCase
    private lateinit var getMovieGenreUseCase: GetMovieGenreUseCase
    private lateinit var addMovieToDatabaseUseCase: AddMovieToDatabaseUseCase
    private lateinit var getAllMoviesFromDatabaseUseCase: GetAllMoviesFromDatabaseUseCase
    private lateinit var deleteAMovieFromDatabaseUseCase: DeleteAMovieFromDatabaseUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        getNowPlayingMoviesUseCase = mockk()
        getTopRatedMoviesUseCase = mockk()
        getPopularMoviesUseCase = mockk()
        searchOnAMovieUseCase = mockk()
        getMovieGenreUseCase = mockk()
        addMovieToDatabaseUseCase = mockk()
        getAllMoviesFromDatabaseUseCase = mockk()
        deleteAMovieFromDatabaseUseCase = mockk()
        moviesViewModel = MoviesViewModel(
            getNowPlayingMoviesUseCase,
            getTopRatedMoviesUseCase,
            getPopularMoviesUseCase,
            searchOnAMovieUseCase,
            getMovieGenreUseCase,
            addMovieToDatabaseUseCase,
            getAllMoviesFromDatabaseUseCase,
            deleteAMovieFromDatabaseUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getNowPlayingMovies_succeed_whenReturnsDataNotEqualNull() = runTest {

        val mockResults = listOf(
            Movie(id = 1, title = "Sample Movie 1", genre_ids = listOf(5, 8)),
            Movie(id = 2, title = "Sample Movie 2", genre_ids = listOf(7, 8))
        )

        val mockApiResponse = Response.success(
            NowPlayingResponse(
                page = 1,
                results = mockResults,
                totalPages = 1,
                totalResults = 1
            )
        )

        coEvery { getNowPlayingMoviesUseCase.execute() } returns mockApiResponse

        moviesViewModel.getNowPlayingMovies()

        assert(moviesViewModel.getNowPlayingMoviesStateFlow.value.data?.get(0)?.title == "Sample Movie 1")
    }
}