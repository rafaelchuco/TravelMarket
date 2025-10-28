package com.example.travelmarket.core.di

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
import com.example.travelmarket.logic.domain.usecases.promotions.GetPromotionDetailUseCase
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
import com.example.travelmarket.logic.viewmodels.destinations.DestinationsListViewModel
import com.example.travelmarket.logic.viewmodels.promotions.PromotionDetailViewModel
import com.example.travelmarket.logic.viewmodels.promotions.PromotionsListViewModel
import com.example.travelmarket.logic.viewmodels.reviews.CreateReviewViewModel
import com.example.travelmarket.logic.viewmodels.reviews.DeleteReviewViewModel
import com.example.travelmarket.logic.viewmodels.reviews.MyReviewsViewModel
import com.example.travelmarket.logic.viewmodels.reviews.ReviewDetailViewModel
import com.example.travelmarket.logic.viewmodels.reviews.ReviewsListViewModel
import com.example.travelmarket.logic.viewmodels.reviews.UpdateReviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetActivitiesUseCase(repository = get()) }
    factory { GetActivityDetailUseCase(repository = get()) }
    factory { RegisterUserUseCase(repository = get()) }
    factory { LoginUseCase(repository = get()) }
    factory { GetProfileUseCase(repository = get()) }
    factory { UpdateProfileUseCase(repository = get()) }
    factory { RefreshTokenUseCase(repository = get()) }
    factory { GetDestinationsUseCase(repository = get()) }
    factory { GetBookingsUseCase(repository = get()) }
    factory { CreateBookingUseCase(repository = get()) }
    factory { GetMyBookingsUseCase(repository = get()) }
    factory { GetBookingByIdUseCase(repository = get()) }
    factory { UpdateBookingUseCase(repository = get()) }
    factory { DeleteBookingUseCase(repository = get()) }
    factory { CancelBookingUseCase(repository = get()) }
    factory { GetPromotionsUseCase(repository = get()) }
    factory { GetPromotionDetailUseCase(repository = get()) }

    // Reviews Use Cases
    factory { GetReviewsUseCase(repository = get()) }
    factory { GetReviewByIdUseCase(repository = get()) }
    factory { CreateReviewUseCase(repository = get()) }
    factory { UpdateReviewUseCase(repository = get()) }
    factory { DeleteReviewUseCase(repository = get()) }
    factory { GetMyReviewsUseCase(repository = get()) }

    viewModel { ActivitiesListViewModel(getActivitiesUseCase = get()) }
    viewModel { ActivityDetailViewModel(getActivityDetailUseCase = get()) }
    viewModel { LoginViewModel(loginUseCase = get()) }
    viewModel { RegisterViewModel(registerUserUseCase = get()) }
    viewModel { ProfileViewModel(getProfileUseCase = get(), updateProfileUseCase = get()) }
    viewModel { DestinationsListViewModel(getDestinationsUseCase = get()) }
    viewModel { BookingsListViewModel(getBookingsUseCase = get()) }
    viewModel { CreateBookingViewModel(createBookingUseCase = get()) }
    viewModel { MyBookingsViewModel(getMyBookingsUseCase = get()) }
    viewModel { BookingDetailViewModel(getBookingByIdUseCase = get()) }
    viewModel { UpdateBookingViewModel(updateBookingUseCase = get()) }
    viewModel { DeleteBookingViewModel(deleteBookingUseCase = get()) }
    viewModel { CancelBookingViewModel(cancelBookingUseCase = get()) }
    viewModel { PromotionsListViewModel(getPromotionsUseCase = get()) }
    viewModel { PromotionDetailViewModel(getPromotionDetailUseCase = get()) }

    // Reviews ViewModels
    viewModel { ReviewsListViewModel(getReviewsUseCase = get()) }
    viewModel { ReviewDetailViewModel(getReviewByIdUseCase = get()) }
    viewModel { CreateReviewViewModel(createReviewUseCase = get()) }
    viewModel { UpdateReviewViewModel(updateReviewUseCase = get()) }
    viewModel { MyReviewsViewModel(getMyReviewsUseCase = get()) }
    viewModel { DeleteReviewViewModel(deleteReviewUseCase = get()) }
}