package com.example.travelmarket.core.di

import com.example.travelmarket.logic.data.repositories.CategoriesRepositoryImpl
import com.example.travelmarket.logic.data.repositories.DestinationsRepositoryImpl
import com.example.travelmarket.logic.data.repositories.FlightsRepositoryImpl
import com.example.travelmarket.logic.data.repositories.HotelsRepositoryImpl
import com.example.travelmarket.logic.data.repositories.InquiriesRepositoryImpl
import com.example.travelmarket.logic.data.repositories.PackagesRepositoryImpl
import com.example.travelmarket.logic.domain.repositories.CategoriesRepository
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import com.example.travelmarket.logic.domain.repositories.FlightsRepository
import com.example.travelmarket.logic.domain.repositories.HotelsRepository
import com.example.travelmarket.logic.domain.repositories.InquiriesRepository
import com.example.travelmarket.logic.domain.repositories.PackagesRepository
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
    abstract fun bindCategoriesRepository(
        impl: CategoriesRepositoryImpl
    ): CategoriesRepository
}
