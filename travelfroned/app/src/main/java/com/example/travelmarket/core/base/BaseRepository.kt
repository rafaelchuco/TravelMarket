package com.example.travelmarket.core.base

import com.example.travelmarket.core.network.NetworkResult
import retrofit2.Response

open class BaseRepository {
    protected suspend fun <T> executeApiCall(call: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error("Respuesta vacía", response.code())
                }
            } else {
                val errorMsg = try { response.errorBody()?.string() } catch (e: Exception) { null }
                NetworkResult.Error(errorMsg ?: "Error ${response.code()}", response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Error desconocido")
        }
    }
}
