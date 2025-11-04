package com.example.travelmarket.di

import com.example.travelmarket.core.storage.TokenManager
import com.example.travelmarket.core.utils.Constants
import com.example.travelmarket.logic.data.remote.activities.ActivitiesApiService
import com.example.travelmarket.logic.data.remote.auth.AuthApiService
import com.example.travelmarket.logic.data.remote.bookings.BookingsApiService
import com.example.travelmarket.logic.data.remote.destinations.DestinationsApiService
import com.example.travelmarket.logic.data.remote.flights.FlightsApiService
import com.example.travelmarket.logic.data.remote.hotels.HotelsApiService
import com.example.travelmarket.logic.data.remote.inquiries.InquiriesApiService
import com.example.travelmarket.logic.data.remote.packages.PackagesApiService
import com.example.travelmarket.logic.data.remote.promotions.PromotionsApiService
import com.example.travelmarket.logic.data.remote.reviews.ReviewsApiService
import com.example.travelmarket.logic.data.remote.wishlist.WishlistApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): Interceptor {
        return Interceptor { chain ->
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

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideActivitiesApiService(retrofit: Retrofit): ActivitiesApiService {
        return retrofit.create(ActivitiesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookingsApiService(retrofit: Retrofit): BookingsApiService {
        return retrofit.create(BookingsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDestinationsApiService(retrofit: Retrofit): DestinationsApiService {
        return retrofit.create(DestinationsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFlightsApiService(retrofit: Retrofit): FlightsApiService {
        return retrofit.create(FlightsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHotelsApiService(retrofit: Retrofit): HotelsApiService {
        return retrofit.create(HotelsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideInquiriesApiService(retrofit: Retrofit): InquiriesApiService {
        return retrofit.create(InquiriesApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePackagesApiService(retrofit: Retrofit): PackagesApiService {
        return retrofit.create(PackagesApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePromotionsApiService(retrofit: Retrofit): PromotionsApiService {
        return retrofit.create(PromotionsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewsApiService(retrofit: Retrofit): ReviewsApiService {
        return retrofit.create(ReviewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWishlistApiService(retrofit: Retrofit): WishlistApiService {
        return retrofit.create(WishlistApiService::class.java)
    }
}