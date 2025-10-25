package com.example.travelmarket.core.di

import com.example.travelmarket.logic.data.remote.packages.PackagesApiService
import com.example.travelmarket.logic.data.repositories.CategoriesRepositoryImpl
import com.example.travelmarket.logic.domain.repositories.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

}
