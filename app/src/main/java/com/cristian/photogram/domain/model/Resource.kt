package com.cristian.photogram.domain.model

sealed class Resource<out T>{
    class Success<out T>(val data: T) : Resource<T>()
    class Loading<out T> : Resource<T>()
    class Error<out T>(val error: Throwable) : Resource<T>()
}
