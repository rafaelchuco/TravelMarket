package com.example.travelmarket.core.utils

import retrofit2.Response
import com.example.travelmarket.core.network.NetworkResult

fun <T> Response<T>.toNetworkResult(): NetworkResult<T> {
    return try {
        if (this.isSuccessful) {
            val body = this.body()
            if (body != null) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error("Response body is null", this.code())
            }
        } else {
            NetworkResult.Error(this.message() ?: "Unknown error", this.code())
        }
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Exception occurred")
    }
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = apiCall.invoke()
        response.toNetworkResult()
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Network error occurred")
    }
}
