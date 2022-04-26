package com.example.itogovoe

import android.content.Context
import com.example.itogovoe.data.api.RetrofitInstance
import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.data.sources.RemoteDataSource
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.database.CurrencyDatabase
import com.example.itogovoe.domain.repository.Repository

class DependencyInjection {

    val remoteDataSource = RetrofitInstance.remoteDataSource
    lateinit var currencyDao: CurrencyDao
    lateinit var localDataSource: LocalDataSource
    lateinit var repository: Repository

    fun initCurrencyDao(context: Context) {
        currencyDao = CurrencyDatabase.getDatabase(context).currencyDao
    }

    fun initLocal(currencyDao: CurrencyDao) {
        localDataSource = LocalDataSource(currencyDao)
    }

    fun initRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource) {
        repository = Repository(localDataSource, remoteDataSource)
    }
}