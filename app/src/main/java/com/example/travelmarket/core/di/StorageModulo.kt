package com.example.travelmarket.core.di

import com.example.travelmarket.core.storage.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single { TokenManager(androidContext()) }
}