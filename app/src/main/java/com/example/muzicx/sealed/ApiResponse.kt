package com.example.muzicx.sealed

sealed class ApiResponse<T>(var data: T? = null , var message: String? = null){
    class Success<T>(data: T) : ApiResponse<T>(data = data)
    class Error<T>(message: String) : ApiResponse<T>(message = message)
}