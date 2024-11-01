package com.example.mymovieapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.response.GenresResponse
import com.example.mymovieapp.domain.usecase.GetMovieGenreUseCase
import com.example.mymovieapp.presentation.viewState.CommonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieGenreUseCase: GetMovieGenreUseCase,
) : ViewModel() {

    private val _getMovieGenreStateFlow = MutableStateFlow<CommonViewState<GenresResponse>>(
        CommonViewState(isIdle = true)
    )
    val getMovieGenreStateFlow: StateFlow<CommonViewState<GenresResponse>> =
        _getMovieGenreStateFlow

    init {
        getMovieGenre()
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

    fun getMovieGenreString(movie: Movie): String? {
        return getMovieGenreStateFlow.value.data?.genres
            ?.filter { it.id in movie.genre_ids }
            ?.joinToString(", ") { it.name }
    }

}