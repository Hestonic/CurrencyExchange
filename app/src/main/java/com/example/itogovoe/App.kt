package com.example.itogovoe

import android.app.Application

class App : Application() {

    val dependencyInjection = DependencyInjection()

    override fun onCreate() {
        super.onCreate()
        dependencyInjection.initCurrencyDao(this)
        dependencyInjection.initHistoryDao(this)
        dependencyInjection.initLocal(dependencyInjection.currencyDao, dependencyInjection.historyDao)
        dependencyInjection.initRepositories(
            dependencyInjection.localDataSource,
            dependencyInjection.remoteDataSource
        )
    }
}