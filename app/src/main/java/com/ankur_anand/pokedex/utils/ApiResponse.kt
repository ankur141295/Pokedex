package com.ankur_anand.pokedex.utils

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception? = null, val message: String) : ApiResponse<Nothing>()
}