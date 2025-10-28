package com.example.travelmarket

import android.app.Application
import com.example.travelmarket.core.di.networkModule
import com.example.travelmarket.core.di.repositoryModule
import com.example.travelmarket.core.di.storageModule
import com.example.travelmarket.core.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TravelMarketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TravelMarketApplication)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                storageModule
            )
        }
    }
}