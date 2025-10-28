package com.example.travelmarket.core.di

import com.example.travelmarket.core.network.NetworkInterceptor
import com.example.travelmarket.core.utils.Constants
import com.example.travelmarket.logic.data.remote.activities.ActivitiesApiService
import com.example.travelmarket.logic.data.remote.auth.AuthApiService
import com.example.travelmarket.logic.data.remote.bookings.BookingsApiService
import com.example.travelmarket.logic.data.remote.destinations.DestinationsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { NetworkInterceptor(tokenManager = get()) }

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(get<NetworkInterceptor>())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ActivitiesApiService::class.java) }
    single { get<Retrofit>().create(AuthApiService::class.java) }
    single { get<Retrofit>().create(DestinationsApiService::class.java) }
    single { get<Retrofit>().create(BookingsApiService::class.java) }
}