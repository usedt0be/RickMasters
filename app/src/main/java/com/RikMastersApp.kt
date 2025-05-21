package com

import android.app.Application
import com.example.rickmasters.data.di.AppContainer
import timber.log.Timber

class RikMastersApp: Application() {
    val appContainer = AppContainer()

    override fun onCreate() {
        super.onCreate()
        configureLogging()
    }

    private fun configureLogging() {
        Timber.plant(Timber.DebugTree())
    }
}