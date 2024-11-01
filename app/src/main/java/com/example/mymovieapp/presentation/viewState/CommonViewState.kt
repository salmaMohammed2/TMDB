package com.example.mymovieapp.presentation.viewState


data class CommonViewState<T>(
    val isIdle: Boolean = false,
    val isEmpty: Boolean = false,
    val data: T? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSuccess: Boolean = false,
    val error: Throwable? = null,
    val isFinished: Boolean? = false,
) : ViewState
