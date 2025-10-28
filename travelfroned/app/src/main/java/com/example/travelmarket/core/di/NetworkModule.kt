package com.example.travelmarket.core.di

import android.content.Context
import com.example.travelmarket.core.network.ApiClient
import com.example.travelmarket.core.storage.TokenManager
import com.example.travelmarket.logic.data.remote.destinations.DestinationsApiService
import com.example.travelmarket.logic.data.remote.flights.FlightsApiService
import com.example.travelmarket.logic.data.remote.hotels.HotelsApiService
import com.example.travelmarket.logic.data.remote.inquiries.InquiriesApiService
import com.example.travelmarket.logic.data.remote.packages.PackagesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        return ApiClient.createOkHttpClient(tokenManager)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return ApiClient.createRetrofit(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideDestinationsApi(retrofit: Retrofit): DestinationsApiService =
        retrofit.create(DestinationsApiService::class.java)

    @Provides
    @Singleton
    fun provideFlightsApi(retrofit: Retrofit): FlightsApiService =
        retrofit.create(FlightsApiService::class.java)

    @Provides
    @Singleton
    fun provideHotelsApi(retrofit: Retrofit): HotelsApiService =
        retrofit.create(HotelsApiService::class.java)

    @Provides
    @Singleton
    fun provideInquiriesApi(retrofit: Retrofit): InquiriesApiService =
        retrofit.create(InquiriesApiService::class.java)

    @Provides
    @Singleton
    fun providePackagesApi(retrofit: Retrofit): PackagesApiService =
        retrofit.create(PackagesApiService::class.java)
}
