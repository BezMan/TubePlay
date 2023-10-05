package com.example.tubeplay.data.model

sealed class ResponseState<out T> {
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val exception: Exception) : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
}
