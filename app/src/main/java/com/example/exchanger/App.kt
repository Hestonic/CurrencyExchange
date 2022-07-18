package com.example.exchanger

import android.app.Application
import com.example.exchanger.ui.main.FilterInstance

class App : Application() {

    val dependencyInjection = DependencyInjection()

    override fun onCreate() {
        super.onCreate()
        if (FilterInstance.filters.value == null) FilterInstance.initFilterLiveData()
        dependencyInjection.initCurrencyDao(this)
        dependencyInjection.initHistoryDao(this)
        dependencyInjection.initLocal(dependencyInjection.currencyDao, dependencyInjection.historyDao)
        dependencyInjection.initRepositories(
            dependencyInjection.localDataSource,
            dependencyInjection.remoteDataSource
        )
    }
}