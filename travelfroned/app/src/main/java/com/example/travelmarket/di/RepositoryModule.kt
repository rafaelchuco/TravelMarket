package com.example.travelmarket.di

import com.example.travelmarket.logic.data.repositories.*
import com.example.travelmarket.logic.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindActivitiesRepository(
        impl: ActivitiesRepositoryImpl
    ): ActivitiesRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindBookingsRepository(
        impl: BookingsRepositoryImpl
    ): BookingsRepository

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(
        impl: CategoriesRepositoryImpl
    ): CategoriesRepository

    @Binds
    @Singleton
    abstract fun bindDestinationsRepository(
        impl: DestinationsRepositoryImpl
    ): DestinationsRepository

    @Binds
    @Singleton
    abstract fun bindFlightsRepository(
        impl: FlightsRepositoryImpl
    ): FlightsRepository

    @Binds
    @Singleton
    abstract fun bindHotelsRepository(
        impl: HotelsRepositoryImpl
    ): HotelsRepository

    @Binds
    @Singleton
    abstract fun bindInquiriesRepository(
        impl: InquiriesRepositoryImpl
    ): InquiriesRepository

    @Binds
    @Singleton
    abstract fun bindPackagesRepository(
        impl: PackagesRepositoryImpl
    ): PackagesRepository

    @Binds
    @Singleton
    abstract fun bindPromotionsRepository(
        impl: PromotionsRepositoryImpl
    ): PromotionsRepository

    @Binds
    @Singleton
    abstract fun bindReviewsRepository(
        impl: ReviewsRepositoryImpl
    ): ReviewsRepository

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(
        impl: com.example.travelmarket.logic.data.repositories.WishlistRepositoryImpl
    ): com.example.travelmarket.logic.domain.repositories.WishlistRepository
}