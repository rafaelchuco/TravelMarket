package com.example.travelmarket.core.base

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.utils.safeApiCall
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T> executeApiCall(
        apiCall: suspend () -> Response<T>
    ): NetworkResult<T> {
        return safeApiCall(apiCall)
    }
}