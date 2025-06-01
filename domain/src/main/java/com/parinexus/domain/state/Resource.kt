package com.parinexus.domain.state

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failed(val message: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}