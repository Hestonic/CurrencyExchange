package com.example.itogovoe

import android.app.Application

class App : Application() {

    val dependencyInjection = DependencyInjection()

    override fun onCreate() {
        super.onCreate()
        dependencyInjection.initCurrencyDao(this)
        dependencyInjection.initLocal(dependencyInjection.currencyDao)
        dependencyInjection.initRepository(
            dependencyInjection.localDataSource,
            dependencyInjection.remoteDataSource
        )
    }
}