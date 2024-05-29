package com.example.profileapplication.common

import retrofit2.Response

suspend fun <T> handleApiResponse(apiCall: suspend () -> Response<T>): ResultWrapper<T?> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val data = response.body()
            ResultWrapper.Success(data)
        } else {
            ResultWrapper.Error("Error Code: ${response.code()} -> ${response.errorBody()?.string()}")
        }
    } catch (e: Exception) {
        ResultWrapper.Error("An error occurred: ${e.message}")
    }
}