package com.sixt.global.application

import android.app.Application
import com.sixt.global.cars.di.carsModule
import com.sixt.global.di.appModule
import com.sixt.global.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SixtApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SixtApplication)
            modules(listOf(appModule, networkModule, carsModule))
        }
    }
}