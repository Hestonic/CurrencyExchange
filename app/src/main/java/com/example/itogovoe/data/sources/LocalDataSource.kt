package com.example.itogovoe.data.sources

import androidx.lifecycle.LiveData
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val currencyDao: CurrencyDao) {

    //HistoryEntity
    suspend fun addHistoryItem(history: HistoryEntity) {
        currencyDao.addHistoryItem(history)
    }

    fun readAllHistory(): List<HistoryEntity> {
        return currencyDao.readAllHistory()
    }

    fun deleteAllHistory() {
        currencyDao.deleteAllHistory()
    }

    fun searchDateHistory(dateTo: Long, dateFrom: Long): LiveData<List<HistoryEntity>> {
        return currencyDao.searchDateHistory(dateTo, dateFrom)
    }


    // InfoEntity
    suspend fun addInfo(info: InfoEntity) {
        currencyDao.addInfo(info)
    }

    fun readAllInfo(): InfoEntity {
        return currencyDao.readAllInfo()
    }

    fun deleteAllInfo() {
        currencyDao.deleteAllInfo()
    }


    // CurrenciesEntity
    suspend fun addCurrencyItem(currencies: CurrenciesEntity) {
        currencyDao.addCurrencyItem(currencies)
    }

    fun readAllCurrencies(): List<CurrenciesEntity> {
        return currencyDao.readAllCurrencies()
    }

    fun deleteAllCurrencies() {
        currencyDao.deleteAllCurrencies()
    }


    /*// CurrenciesUiEntity
    suspend fun addCurrencyUiItem(currencyDaoEntity: CurrenciesDaoEntity) {
        currencyDao.addCurrencyUiItem(currencyDaoEntity)
    }

    suspend fun updateCurrencyUiItem(currencyDaoEntity: CurrenciesDaoEntity) {
        currencyDao.updateCurrencyUiItem(currencyDaoEntity)
    }

    fun readAllCurrenciesUi(): List<CurrenciesDaoEntity> {
        return currencyDao.readAllCurrenciesUi()
    }

    fun deleteAllCurrenciesUi() {
        currencyDao.deleteAllCurrenciesUi()
    }

    fun deleteCurrencyUiItem(name: String) {
        currencyDao.deleteCurrencyUiItem(name)
    }*/
}

