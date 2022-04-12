package com.example.itogovoe.data.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesUiEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity

class LocalDataSource(private val currencyDao: CurrencyDao) {

    //HistoryEntity
    suspend fun addHistoryItem(history: HistoryEntity) {
        currencyDao.addHistoryItem(history)
    }

    fun readAllHistory(): LiveData<List<HistoryEntity>> {
        return currencyDao.readAllHistory()
    }

    fun deleteAllHistory() {
        currencyDao.deleteAllHistory()
    }


    // InfoEntity
    suspend fun addInfo(info: InfoEntity) {
        currencyDao.addInfo(info)
    }

    fun readAllInfo() : InfoEntity {
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


    // CurrenciesUiEntity
    suspend fun addCurrencyUiItem(currenciesUi: CurrenciesUiEntity) {
        currencyDao.addCurrencyUiItem(currenciesUi)
    }

    fun readAllCurrenciesUi() : LiveData<List<CurrenciesUiEntity>> {
        return currencyDao.readAllCurrenciesUi()
    }

    fun deleteAllCurrenciesUi() {
        currencyDao.deleteAllCurrenciesUi()
    }

    fun deleteCurrencyUiItem(name: String) {
        currencyDao.deleteCurrencyUiItem(name)
    }
}

