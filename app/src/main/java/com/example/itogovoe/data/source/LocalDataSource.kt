package com.example.itogovoe.data.source

import androidx.lifecycle.LiveData
import com.example.itogovoe.App
import com.example.itogovoe.data.source.local_source.dao.CurrencyDao
import com.example.itogovoe.data.source.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.source.local_source.entities.InfoEntity

class LocalDataSource(private val currencyDao: CurrencyDao) {

    // private val currencyDao = App().getInstance().getCurrencyDatabase().currencyDao

    // CurrenciesEntity
    fun readAllCurrencies() : List<CurrenciesEntity> {
        return currencyDao.readAllCurrencies()
    }

    suspend fun addCurrencyItem(currencies: CurrenciesEntity) {
        currencyDao.addCurrencyItem(currencies)
    }

    fun deleteAllCurrencies() {
        currencyDao.deleteAllCurrencies()
    }

    // InfoEntity
    suspend fun addInfo(info: InfoEntity) {
        currencyDao.addInfo(info)
    }
}

