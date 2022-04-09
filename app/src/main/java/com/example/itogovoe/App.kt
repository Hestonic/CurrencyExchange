package com.example.itogovoe

import android.app.Application
import com.example.itogovoe.data.source.local_source.database.CurrencyDatabase

class App : Application() {

    // private lateinit var currencyDatabase: CurrencyDatabase
    val dependencyInjection = DependencyInjection()

    override fun onCreate() {
        super.onCreate()
        // currencyDatabase = CurrencyDatabase.getDatabase(this)
        dependencyInjection.initCurrencyDao(this)
        dependencyInjection.initLocal(dependencyInjection.currencyDao)
        // dependencyInjection.initRemote(dependencyInjection.api)
        dependencyInjection.initRepository(
            dependencyInjection.localDataSource,
            dependencyInjection.remoteDataSource
        )
    }
}