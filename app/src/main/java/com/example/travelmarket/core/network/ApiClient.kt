package com.example.travelmarket.core.network

import com.example.travelmarket.core.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    // Interceptor para agregar token de autenticación
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        // TODO: Obtener token de DataStore/SharedPreferences
        val token = "" // Placeholder - se implementará con DataStore
        
        val requestBuilder = original.newBuilder()
            .header(Constants.HEADER_CONTENT_TYPE, Constants.CONTENT_TYPE_JSON)
        
        if (token.isNotEmpty()) {
            requestBuilder.header(
                Constants.HEADER_AUTHORIZATION,
                "${Constants.TOKEN_PREFIX}$token"
            )
        }
        
        chain.proceed(requestBuilder.build())
    }
    
    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
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
    val authApiService = retrofit.create(com.example.travelmarket.data.remote.AuthApiService::class.java)
    val packagesApiService = retrofit.create(com.example.travelmarket.data.remote.PackagesApiService::class.java)
    val bookingsApiService = retrofit.create(com.example.travelmarket.data.remote.BookingsApiService::class.java)
}

