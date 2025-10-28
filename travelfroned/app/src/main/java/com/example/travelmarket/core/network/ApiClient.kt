package com.example.travelmarket.core.network

import com.example.travelmarket.core.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
    
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
    
    // Instancias de los servicios API
    val packagesApiService = retrofit.create(com.example.travelmarket.data.remote.PackagesApiService::class.java)
    val bookingsApiService = retrofit.create(com.example.travelmarket.data.remote.BookingsApiService::class.java)
}

