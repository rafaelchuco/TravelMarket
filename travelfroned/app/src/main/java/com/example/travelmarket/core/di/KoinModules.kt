package com.example.travelmarket.core.di

import com.example.travelmarket.core.network.NetworkInterceptor
import com.example.travelmarket.core.storage.TokenManager
import com.example.travelmarket.core.utils.Constants
import com.example.travelmarket.logic.data.remote.activities.ActivitiesApiService
import com.example.travelmarket.logic.data.remote.auth.AuthApiService
import com.example.travelmarket.logic.data.remote.bookings.BookingsApiService
import com.example.travelmarket.logic.data.repositories.ActivitiesRepositoryImpl
import com.example.travelmarket.logic.data.repositories.AuthRepositoryImpl
import com.example.travelmarket.logic.data.repositories.BookingsRepositoryImpl
import com.example.travelmarket.logic.domain.repositories.ActivitiesRepository
import com.example.travelmarket.logic.domain.repositories.AuthRepository
import com.example.travelmarket.logic.domain.repositories.BookingsRepository
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
import com.example.travelmarket.logic.domain.usecases.destinations.GetDestinationsUseCase
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
import com.example.travelmarket.logic.viewmodels.destinations.DestinationsListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// ✅ Módulo de almacenamiento (TokenManager)
val koinStorageModule = module {
    single { TokenManager(androidContext()) }
}

// ✅ Módulo de red (Retrofit con Gson para Koin)
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
}

// ✅ Módulo de repositorios (para Auth, Activities, Bookings)
val koinRepositoryModule = module {
    single<ActivitiesRepository> { ActivitiesRepositoryImpl(apiService = get()) }
    single<AuthRepository> { AuthRepositoryImpl(apiService = get()) }
    single<BookingsRepository> { BookingsRepositoryImpl(apiService = get()) }
}

// ✅ Módulo de casos de uso y ViewModels
val useCaseModule = module {
    // ✅ Activities
    factory { GetActivitiesUseCase(repository = get()) }
    factory { GetActivityDetailUseCase(repository = get()) }

    // ✅ Auth
    factory { RegisterUserUseCase(repository = get()) }
    factory { LoginUseCase(repository = get()) }
    factory { GetProfileUseCase(repository = get()) }
    factory { UpdateProfileUseCase(repository = get()) }
    factory { RefreshTokenUseCase(repository = get()) }

    // ✅ Bookings
    factory { GetBookingsUseCase(repository = get()) }
    factory { CreateBookingUseCase(repository = get()) }
    factory { GetMyBookingsUseCase(repository = get()) }
    factory { GetBookingByIdUseCase(repository = get()) }
    factory { UpdateBookingUseCase(repository = get()) }
    factory { DeleteBookingUseCase(repository = get()) }
    factory { CancelBookingUseCase(repository = get()) }

    // ✅ ViewModels - Solo para Activities, Auth, Bookings
    viewModel { ActivitiesListViewModel(getActivitiesUseCase = get()) }
    viewModel { ActivityDetailViewModel(getActivityDetailUseCase = get()) }
    viewModel { LoginViewModel(loginUseCase = get()) }
    viewModel { RegisterViewModel(registerUserUseCase = get()) }
    viewModel { ProfileViewModel(getProfileUseCase = get(), updateProfileUseCase = get()) }
    viewModel { BookingsListViewModel(getBookingsUseCase = get()) }
    viewModel { CreateBookingViewModel(createBookingUseCase = get()) }
    viewModel { MyBookingsViewModel(getMyBookingsUseCase = get()) }
    viewModel { BookingDetailViewModel(getBookingByIdUseCase = get()) }
    viewModel { UpdateBookingViewModel(updateBookingUseCase = get()) }
    viewModel { DeleteBookingViewModel(deleteBookingUseCase = get()) }
    viewModel { CancelBookingViewModel(cancelBookingUseCase = get()) }
}