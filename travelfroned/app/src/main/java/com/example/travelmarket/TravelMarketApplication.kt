package com.example.travelmarket

import android.app.Application
import com.example.travelmarket.di.appModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class TravelMarketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Hilt se inicializa automáticamente con @HiltAndroidApp
        
        // Inicializar Koin para pantallas que lo requieren
        startKoin {
            androidContext(this@TravelMarketApplication)
            modules(appModule)
        }
    }
}
