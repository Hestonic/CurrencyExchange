package com.example.itogovoe

import android.app.Application
import com.example.itogovoe.ui.main.FilterInstance

class App : Application() {

    val dependencyInjection = DependencyInjection()

    override fun onCreate() {
        super.onCreate()
        if (FilterInstance.timeFilter.value == null) FilterInstance.initFilterLiveData()
        dependencyInjection.initCurrencyDao(this)
        dependencyInjection.initHistoryDao(this)
        dependencyInjection.initLocal(dependencyInjection.currencyDao, dependencyInjection.historyDao)
        dependencyInjection.initRepositories(
            dependencyInjection.localDataSource,
            dependencyInjection.remoteDataSource
        )
    }
}