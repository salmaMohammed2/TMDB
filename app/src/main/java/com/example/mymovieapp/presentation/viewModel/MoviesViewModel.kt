package com.example.mymovieapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.usecase.AddMovieToDatabaseUseCase
import com.example.mymovieapp.domain.usecase.DeleteAMovieFromDatabaseUseCase
import com.example.mymovieapp.domain.usecase.GetAllMoviesFromDatabaseUseCase
import com.example.mymovieapp.domain.usecase.GetMovieGenreUseCase
import com.example.mymovieapp.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.mymovieapp.domain.usecase.GetPopularMoviesUseCase
import com.example.mymovieapp.domain.usecase.GetTopRatedMoviesUseCase
import com.example.mymovieapp.domain.usecase.SearchOnAMovieUseCase
import com.example.mymovieapp.presentation.viewState.CommonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchOnAMovieUseCase: SearchOnAMovieUseCase,
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
    private val addMovieToDatabaseUseCase: AddMovieToDatabaseUseCase,
    private val getAllMoviesFromDatabaseUseCase: GetAllMoviesFromDatabaseUseCase,
    private val deleteAMovieFromDatabaseUseCase: DeleteAMovieFromDatabaseUseCase
) : ViewModel() {

    private val _getNowPlayingMoviesStateFlow =
        MutableStateFlow<CommonViewState<List<Movie>>>(
            CommonViewState(isIdle = true)
        )
    val getNowPlayingMoviesStateFlow: StateFlow<CommonViewState<List<Movie>>> =
        _getNowPlayingMoviesStateFlow

    private val _getTopRatedMoviesStateFlow = MutableStateFlow<CommonViewState<List<Movie>>>(
        CommonViewState(isIdle = true)
    )
    val getTopRatedMoviesStateFlow: StateFlow<CommonViewState<List<Movie>>> =
        _getTopRatedMoviesStateFlow

    private val _getPopularMoviesStateFlow = MutableStateFlow<CommonViewState<List<Movie>>>(
        CommonViewState(isIdle = true)
    )
    val getPopularMoviesStateFlow: StateFlow<CommonViewState<List<Movie>>> =
        _getPopularMoviesStateFlow

    private val _searchOnAMovieStateFlow = MutableStateFlow<CommonViewState<List<Movie>>>(
        CommonViewState(isIdle = true)
    )
    val searchOnAMovieStateFlow: StateFlow<CommonViewState<List<Movie>>> =
        _searchOnAMovieStateFlow

    private val _getMovieGenreStateFlow = MutableStateFlow<CommonViewState<GenresResponse>>(
        CommonViewState(isIdle = true)
    )
    val getMovieGenreStateFlow: StateFlow<CommonViewState<GenresResponse>> =
        _getMovieGenreStateFlow

    private val _getAllMoviesFromDatabaseStateFlow = MutableStateFlow<CommonViewState<List<Movie>>>(
        CommonViewState(isIdle = true)
    )
    val getAllMoviesFromDatabaseStateFlow: StateFlow<CommonViewState<List<Movie>>> =
        _getAllMoviesFromDatabaseStateFlow

    val tabPosition = MutableStateFlow(0)

    init {
        getNowPlayingMovies()
        getAllMoviesFromDatabase()
    }

    fun fetchData() {
        when (tabPosition.value) {
            0 -> getNowPlayingMovies()
            1 -> getTopRatedMovies()
            2 -> getPopularMovies()
            4 -> getAllMoviesFromDatabase()
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            try {
                _getNowPlayingMoviesStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getNowPlayingMoviesUseCase.execute()
                _getNowPlayingMoviesStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()?.results
                    )
                )

            } catch (t: Throwable) {
                _getNowPlayingMoviesStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                _getTopRatedMoviesStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getTopRatedMoviesUseCase.execute()
                _getTopRatedMoviesStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()?.results
                    )
                )

            } catch (t: Throwable) {
                _getTopRatedMoviesStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                _getPopularMoviesStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getPopularMoviesUseCase.execute()
                _getPopularMoviesStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()?.results
                    )
                )

            } catch (t: Throwable) {
                _getPopularMoviesStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launch {
            try {
                _searchOnAMovieStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = searchOnAMovieUseCase.execute(movieName)
                _searchOnAMovieStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()?.results
                    )
                )

            } catch (t: Throwable) {
                _searchOnAMovieStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    private fun getMovieGenre() {
        viewModelScope.launch {
            try {
                _getMovieGenreStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getMovieGenreUseCase.execute()
                _getMovieGenreStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response.body()
                    )
                )

            } catch (t: Throwable) {
                _getMovieGenreStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    fun getAllMoviesFromDatabase() {
        viewModelScope.launch {
            try {
                _getAllMoviesFromDatabaseStateFlow.emit(
                    CommonViewState(isLoading = true)
                )
                val response = getAllMoviesFromDatabaseUseCase.execute()
                _getAllMoviesFromDatabaseStateFlow.emit(
                    CommonViewState(
                        isSuccess = true,
                        data = response
                    )
                )

            } catch (t: Throwable) {
                _getAllMoviesFromDatabaseStateFlow.emit(
                    CommonViewState(
                        error = t
                    )
                )
            }
        }
    }

    fun addAMovieToMyFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                addMovieToDatabaseUseCase.execute(movie)
            } catch (t: Throwable) {
            }
        }
    }

    fun deleteAMovieFromMyFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                deleteAMovieFromDatabaseUseCase.execute(movieId)
            } catch (t: Throwable) {
            }
        }
    }

    fun updateMovieListWithFavorites(
        movieList: List<Movie>,
        favoriteList: List<Movie>
    ): List<Movie> {
        val favoriteIds =
            favoriteList.map { it.id }.toSet()
        movieList.forEach { movie ->
            movie.isFavorite =
                favoriteIds.contains(movie.id)
        }
        return movieList
    }

    fun checkTabLayoutPosition(position: Int): StateFlow<CommonViewState<List<Movie>>> {
        return when (position) {
            0 -> getNowPlayingMoviesStateFlow
            1 -> getTopRatedMoviesStateFlow
            2 -> getPopularMoviesStateFlow
            3 -> getAllMoviesFromDatabaseStateFlow
            else -> getNowPlayingMoviesStateFlow
        }
    }


}