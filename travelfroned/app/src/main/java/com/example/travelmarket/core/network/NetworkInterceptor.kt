package com.example.travelmarket.core.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")

        // Si usas JWT, añadir token aquí
        // TokenManager.getToken()?.let { token ->
        //     builder.header("Authorization", "Bearer $token")
        // }

        return chain.proceed(builder.build())
    }
}
