package com.example.travelmarket.core.di

import com.example.travelmarket.logic.data.repositories.ActivitiesRepositoryImpl
import com.example.travelmarket.logic.data.repositories.AuthRepositoryImpl
import com.example.travelmarket.logic.data.repositories.BookingsRepositoryImpl
import com.example.travelmarket.logic.data.repositories.DestinationsRepositoryImpl
import com.example.travelmarket.logic.domain.repositories.ActivitiesRepository
import com.example.travelmarket.logic.domain.repositories.AuthRepository
import com.example.travelmarket.logic.domain.repositories.BookingsRepository
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ActivitiesRepository> { ActivitiesRepositoryImpl(apiService = get()) }
    single<AuthRepository> { AuthRepositoryImpl(apiService = get()) }
    single<DestinationsRepository> { DestinationsRepositoryImpl(apiService = get()) }
    single<BookingsRepository> { BookingsRepositoryImpl(apiService = get()) }
}