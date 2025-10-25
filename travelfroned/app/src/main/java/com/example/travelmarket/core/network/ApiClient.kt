package com.example.travelmarket.core.network

import com.example.travelmarket.core.utils.AppConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
        .addInterceptor(NetworkInterceptor())
        .connectTimeout(AppConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(AppConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(AppConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    // ✅ Configurar Moshi con KotlinJsonAdapterFactory
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AppConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))  // ✅ Usar moshi configurado
        .client(client)
        .build()
}
