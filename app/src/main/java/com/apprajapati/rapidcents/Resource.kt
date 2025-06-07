package com.apprajapati.rapidcents

sealed class Resource<T: Any?> {
    class Success<T: Any>(val data: T) : Resource<T>()
    class Error<T: Any>(val message: String?, val data: T? = null) : Resource<T>()
    class Loading<T: Any>(val data: T? = null) : Resource<T>()
}