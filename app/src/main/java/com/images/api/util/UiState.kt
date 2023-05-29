package com.images.api.util

/**
 * Created by ZEESHAN on 5/13/2023.
 */
sealed class UiState<out T> {
    object Initial : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Fail(
        val error: Throwable
    ) : UiState<Nothing>()

}
