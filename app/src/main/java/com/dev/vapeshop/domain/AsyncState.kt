package com.dev.vapeshop.domain

sealed class AsyncState<out T> {

    object Loading : AsyncState<Nothing>()
    data class Error(val message: String) :
        AsyncState<Nothing>()

    data class Loaded<T>(val data: T) : AsyncState<T>()
}