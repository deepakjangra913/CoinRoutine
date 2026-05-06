package com.deepak.coinroutine

import android.app.Application
import com.deepak.coinroutine.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

/**
 * Android Application class for the CoinRoutine app.
 * Initializes Koin for dependency injection.
 */
class CoinRoutineApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@CoinRoutineApplication)
        }
    }
}