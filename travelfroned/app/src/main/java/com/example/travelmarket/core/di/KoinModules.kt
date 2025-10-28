package com.example.travelmarket.core.di

import com.example.travelmarket.core.network.NetworkInterceptor
import com.example.travelmarket.core.storage.TokenManager
import com.example.travelmarket.core.utils.Constants
import com.example.travelmarket.logic.data.remote.activities.ActivitiesApiService
import com.example.travelmarket.logic.data.remote.auth.AuthApiService
import com.example.travelmarket.logic.data.remote.bookings.BookingsApiService
import com.example.travelmarket.logic.data.remote.promotions.PromotionsApiService
import com.example.travelmarket.logic.data.remote.reviews.ReviewsApiService
import com.example.travelmarket.logic.data.repositories.ActivitiesRepositoryImpl
import com.example.travelmarket.logic.data.repositories.AuthRepositoryImpl
import com.example.travelmarket.logic.data.repositories.BookingsRepositoryImpl
import com.example.travelmarket.logic.data.repositories.PromotionsRepositoryImpl
import com.example.travelmarket.logic.data.repositories.ReviewsRepositoryImpl
import com.example.travelmarket.logic.domain.repositories.ActivitiesRepository
import com.example.travelmarket.logic.domain.repositories.AuthRepository
import com.example.travelmarket.logic.domain.repositories.BookingsRepository
import com.example.travelmarket.logic.domain.repositories.PromotionsRepository
import com.example.travelmarket.logic.domain.repositories.ReviewsRepository
import com.example.travelmarket.logic.domain.usecases.activities.GetActivitiesUseCase
import com.example.travelmarket.logic.domain.usecases.activities.GetActivityDetailUseCase
import com.example.travelmarket.logic.domain.usecases.auth.GetProfileUseCase
import com.example.travelmarket.logic.domain.usecases.auth.LoginUseCase
import com.example.travelmarket.logic.domain.usecases.auth.RefreshTokenUseCase
import com.example.travelmarket.logic.domain.usecases.auth.RegisterUserUseCase
import com.example.travelmarket.logic.domain.usecases.auth.UpdateProfileUseCase
import com.example.travelmarket.logic.domain.usecases.bookings.CancelBookingUseCase
import com.example.travelmarket.logic.domain.usecases.bookings.CreateBookingUseCase
import com.example.travelmarket.logic.domain.usecases.bookings.DeleteBookingUseCase
import com.example.travelmarket.logic.domain.usecases.bookings.GetBookingByIdUseCase
import com.example.travelmarket.logic.domain.usecases.bookings.GetBookingsUseCase
import com.example.travelmarket.logic.domain.usecases.bookings.GetMyBookingsUseCase
import com.example.travelmarket.logic.domain.usecases.bookings.UpdateBookingUseCase
import com.example.travelmarket.logic.domain.usecases.promotions.GetPromotionsUseCase
import com.example.travelmarket.logic.domain.usecases.reviews.CreateReviewUseCase
import com.example.travelmarket.logic.domain.usecases.reviews.DeleteReviewUseCase
import com.example.travelmarket.logic.domain.usecases.reviews.GetMyReviewsUseCase
import com.example.travelmarket.logic.domain.usecases.reviews.GetReviewByIdUseCase
import com.example.travelmarket.logic.domain.usecases.reviews.GetReviewsUseCase
import com.example.travelmarket.logic.domain.usecases.reviews.UpdateReviewUseCase
import com.example.travelmarket.logic.viewmodels.activities.ActivitiesListViewModel
import com.example.travelmarket.logic.viewmodels.activities.ActivityDetailViewModel
import com.example.travelmarket.logic.viewmodels.auth.LoginViewModel
import com.example.travelmarket.logic.viewmodels.auth.ProfileViewModel
import com.example.travelmarket.logic.viewmodels.auth.RegisterViewModel
import com.example.travelmarket.logic.viewmodels.bookings.BookingDetailViewModel
import com.example.travelmarket.logic.viewmodels.bookings.BookingsListViewModel
import com.example.travelmarket.logic.viewmodels.bookings.CancelBookingViewModel
import com.example.travelmarket.logic.viewmodels.bookings.CreateBookingViewModel
import com.example.travelmarket.logic.viewmodels.bookings.DeleteBookingViewModel
import com.example.travelmarket.logic.viewmodels.bookings.MyBookingsViewModel
import com.example.travelmarket.logic.viewmodels.bookings.UpdateBookingViewModel
import com.example.travelmarket.logic.viewmodels.promotions.PromotionsListViewModel
import com.example.travelmarket.logic.viewmodels.reviews.CreateReviewViewModel
import com.example.travelmarket.logic.viewmodels.reviews.DeleteReviewViewModel
import com.example.travelmarket.logic.viewmodels.reviews.MyReviewsViewModel
import com.example.travelmarket.logic.viewmodels.reviews.ReviewDetailViewModel
import com.example.travelmarket.logic.viewmodels.reviews.ReviewsListViewModel
import com.example.travelmarket.logic.viewmodels.reviews.UpdateReviewViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val koinStorageModule = module {
    single { TokenManager(androidContext()) }
}

val koinNetworkModule = module {
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
    single { get<Retrofit>().create(BookingsApiService::class.java) }
    single { get<Retrofit>().create(PromotionsApiService::class.java) }
    single { get<Retrofit>().create(ReviewsApiService::class.java) }
}

val koinRepositoryModule = module {
    single<ActivitiesRepository> { ActivitiesRepositoryImpl(apiService = get()) }
    single<AuthRepository> { AuthRepositoryImpl(apiService = get()) }
    single<BookingsRepository> { BookingsRepositoryImpl(apiService = get()) }
    single<PromotionsRepository> { PromotionsRepositoryImpl(apiService = get()) }
    single<ReviewsRepository> { ReviewsRepositoryImpl(apiService = get()) }
}

val useCaseModule = module {
    // ========== ACTIVITIES ==========
    factory { GetActivitiesUseCase(repository = get()) }
    factory { GetActivityDetailUseCase(repository = get()) }

    // ========== AUTH ==========
    factory { RegisterUserUseCase(repository = get()) }
    factory { LoginUseCase(repository = get()) }
    factory { GetProfileUseCase(repository = get()) }
    factory { UpdateProfileUseCase(repository = get()) }
    factory { RefreshTokenUseCase(repository = get()) }

    // ========== BOOKINGS ==========
    factory { GetBookingsUseCase(repository = get()) }
    factory { CreateBookingUseCase(repository = get()) }
    factory { GetMyBookingsUseCase(repository = get()) }
    factory { GetBookingByIdUseCase(repository = get()) }
    factory { UpdateBookingUseCase(repository = get()) }
    factory { DeleteBookingUseCase(repository = get()) }
    factory { CancelBookingUseCase(repository = get()) }

    // ========== PROMOTIONS (ALEX) ==========
    factory { GetPromotionsUseCase(repository = get()) }

    // ========== REVIEWS (ALEX) ==========
    factory { GetReviewsUseCase(repository = get()) }
    factory { CreateReviewUseCase(repository = get()) }
    factory { GetMyReviewsUseCase(repository = get()) }
    factory { GetReviewByIdUseCase(repository = get()) }
    factory { UpdateReviewUseCase(repository = get()) }
    factory { DeleteReviewUseCase(repository = get()) }

    // ========== VIEWMODELS - ACTIVITIES ==========
    viewModel { ActivitiesListViewModel(getActivitiesUseCase = get()) }
    viewModel { ActivityDetailViewModel(getActivityDetailUseCase = get()) }

    // ========== VIEWMODELS - AUTH ==========
    viewModel { LoginViewModel(loginUseCase = get()) }
    viewModel { RegisterViewModel(registerUserUseCase = get()) }
    viewModel { ProfileViewModel(getProfileUseCase = get(), updateProfileUseCase = get()) }

    // ========== VIEWMODELS - BOOKINGS ==========
    viewModel { BookingsListViewModel(getBookingsUseCase = get()) }
    viewModel { CreateBookingViewModel(createBookingUseCase = get()) }
    viewModel { MyBookingsViewModel(getMyBookingsUseCase = get()) }
    viewModel { BookingDetailViewModel(getBookingByIdUseCase = get()) }
    viewModel { UpdateBookingViewModel(updateBookingUseCase = get()) }
    viewModel { DeleteBookingViewModel(deleteBookingUseCase = get()) }
    viewModel { CancelBookingViewModel(cancelBookingUseCase = get()) }

    // ========== VIEWMODELS - PROMOTIONS (ALEX) ==========
    viewModel { PromotionsListViewModel(getPromotionsUseCase = get()) }

    // ========== VIEWMODELS - REVIEWS (ALEX) ==========
    viewModel { ReviewsListViewModel(getReviewsUseCase = get()) }
    viewModel { CreateReviewViewModel(createReviewUseCase = get(), reviewsRepository = get()) }
    viewModel { MyReviewsViewModel(getMyReviewsUseCase = get()) }
    viewModel { ReviewDetailViewModel(getReviewByIdUseCase = get()) }
    viewModel { UpdateReviewViewModel(updateReviewUseCase = get()) }
    viewModel { DeleteReviewViewModel(deleteReviewUseCase = get()) }
}
