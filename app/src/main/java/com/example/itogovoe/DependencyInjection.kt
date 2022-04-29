package com.example.itogovoe

import android.content.Context
import com.example.itogovoe.data.api.RetrofitInstance
import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.data.sources.RemoteDataSource
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.CurrencyDatabase
import com.example.itogovoe.data.sources.local_source.dao.HistoryDao
import com.example.itogovoe.domain.repository.Repository

class DependencyInjection {

    val remoteDataSource = RetrofitInstance.remoteDataSource
    lateinit var currencyDao: CurrencyDao
    lateinit var historyDao: HistoryDao
    lateinit var localDataSource: LocalDataSource
    lateinit var repository: Repository

    fun initCurrencyDao(context: Context) {
        currencyDao = CurrencyDatabase.getDatabase(context).currencyDao
    }

    fun initHistoryDao(context: Context) {
        historyDao = CurrencyDatabase.getDatabase(context).historyDao
    }

    fun initLocal(currencyDao: CurrencyDao, historyDao: HistoryDao) {
        localDataSource = LocalDataSource(currencyDao, historyDao)
    }

    fun initRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource) {
        repository = Repository(localDataSource, remoteDataSource)
    }
}