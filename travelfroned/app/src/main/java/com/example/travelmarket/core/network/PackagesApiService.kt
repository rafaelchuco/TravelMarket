package com.example.travelmarket.core.network

import retrofit2.http.GET

interface PackagesApiService {
    @GET("packages/")
    suspend fun getPackages(): Any
}

