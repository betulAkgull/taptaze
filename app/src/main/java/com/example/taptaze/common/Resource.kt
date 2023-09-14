package com.example.taptaze.common

sealed class Resource<out T: Any>{
    data class Success<out T: Any>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    object Loading: Resource<Nothing>()
}
