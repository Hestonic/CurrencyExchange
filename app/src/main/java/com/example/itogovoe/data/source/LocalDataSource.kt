package com.example.itogovoe.data.source

import androidx.lifecycle.LiveData
import com.example.itogovoe.App
import com.example.itogovoe.data.source.local_source.dao.CurrencyDao
import com.example.itogovoe.data.source.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.source.local_source.entities.InfoEntity

class LocalDataSource(private val currencyDao: CurrencyDao) {

    // InfoEntity
    suspend fun addInfo(info: InfoEntity) {
        currencyDao.addInfo(info)
    }

    fun readAllInfo() : InfoEntity{
        return currencyDao.readAllInfo()
    }

    fun deleteAllInfo() {
        currencyDao.deleteAllInfo()
    }


    // CurrenciesEntity
    suspend fun addCurrencyItem(currencies: CurrenciesEntity) {
        currencyDao.addCurrencyItem(currencies)
    }

    fun readAllCurrencies() : List<CurrenciesEntity> {
        return currencyDao.readAllCurrencies()
    }

    fun deleteAllCurrencies() {
        currencyDao.deleteAllCurrencies()
    }


}

