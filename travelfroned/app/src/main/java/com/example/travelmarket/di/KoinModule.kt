package com.example.travelmarket.di

import com.example.travelmarket.core.storage.TokenManager
import com.example.travelmarket.core.utils.Constants
import com.example.travelmarket.logic.data.repositories.BookingsRepositoryImpl
import com.example.travelmarket.logic.data.remote.bookings.BookingsApiService
import com.example.travelmarket.logic.domain.repositories.BookingsRepository
import com.example.travelmarket.logic.domain.usecases.bookings.CreateBookingUseCase
import com.example.travelmarket.logic.viewmodels.bookings.CreateBookingViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // Proporcionar TokenManager (necesario para el interceptor)
    single { TokenManager(androidContext()) }
    
    // Proporcionar HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    
    // Proporcionar AuthInterceptor
    single {
        val tokenManager: TokenManager = get()
        Interceptor { chain ->
            val original = chain.request()
            val token = runBlocking { tokenManager.getAccessToken().first() }
            
            val requestBuilder = original.newBuilder()
                .header(Constants.HEADER_CONTENT_TYPE, Constants.CONTENT_TYPE_JSON)
            
            if (!token.isNullOrEmpty()) {
                requestBuilder.header(
                    Constants.HEADER_AUTHORIZATION,
                    "${Constants.TOKEN_PREFIX}$token"
                )
            }
            
            chain.proceed(requestBuilder.build())
        }
    }
    
    // Proporcionar OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }
    
    // Proporcionar Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }
    
    // Proporcionar BookingsApiService
    single<BookingsApiService> {
        get<Retrofit>().create(BookingsApiService::class.java)
    }
    
    // Proporcionar BookingsRepository
    single<BookingsRepository> { 
        BookingsRepositoryImpl(get<BookingsApiService>())
    }
    
    // Proporcionar CreateBookingUseCase
    single { CreateBookingUseCase(get<BookingsRepository>()) }
    
    // Proporcionar CreateBookingViewModel
    viewModel { CreateBookingViewModel(get<CreateBookingUseCase>()) }
}

