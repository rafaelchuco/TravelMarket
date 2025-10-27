package com.example.travelmarket.core.network

import android.util.Log
import com.example.travelmarket.core.storage.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()

        val token = tokenManager.getAccessToken()
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()

        Log.d("API_REQUEST", "URL: ${request.url}")
        Log.d("API_REQUEST", "Method: ${request.method}")
        Log.d("API_REQUEST", "Headers: ${request.headers}")

        val response = chain.proceed(request)

        Log.d("API_RESPONSE", "Code: ${response.code}")
        Log.d("API_RESPONSE", "Message: ${response.message}")

        return response
    }
}