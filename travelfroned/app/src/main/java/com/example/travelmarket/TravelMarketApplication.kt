package com.example.travelmarket

import android.app.Application
import com.example.travelmarket.core.di.koinNetworkModule
import com.example.travelmarket.core.di.koinRepositoryModule
import com.example.travelmarket.core.di.koinStorageModule
import com.example.travelmarket.core.di.useCaseModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class TravelMarketApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // ✅ Inicializar Koin (solo para Auth, Activities, Bookings)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TravelMarketApplication)
            modules(
                koinStorageModule,
                koinNetworkModule,
                koinRepositoryModule,
                useCaseModule
            )
        }
    }
}