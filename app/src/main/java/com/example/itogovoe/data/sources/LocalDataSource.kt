package com.example.itogovoe.data.sources

import android.util.Log
import com.example.itogovoe.data.sources.local_source.DateConverter
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import java.time.LocalDateTime

class LocalDataSource(private val currencyDao: CurrencyDao) {

    //HistoryEntity
    suspend fun addHistoryItem(history: HistoryEntity) {
        currencyDao.addHistoryItem(history)
    }

    fun readAllHistory(): List<HistoryEntity> {
        return currencyDao.readAllHistory()
    }

    fun searchDateHistory(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<HistoryEntity> {
        val dateFromTimestamp = DateConverter().localDateTimeToTimestamp(dateFrom)
        val dateToTimestamp = DateConverter().localDateTimeToTimestamp(dateTo)
        Log.d("date_timestamp_tag", "dateFrom $dateFromTimestamp, dateTo $dateToTimestamp")
        return currencyDao.searchDateHistory(dateFromTimestamp, dateToTimestamp)
    }


    // CurrenciesEntity
    suspend fun addCurrencyItem(currencies: CurrenciesEntity) {
        currencyDao.addCurrencyItem(currencies)
    }

    fun readAllCurrencies(): List<CurrenciesEntity> {
        return currencyDao.readAllCurrencies()
    }

    suspend fun updateCurrency(currency: CurrenciesEntity) {
        currencyDao.updateCurrency(currency)
    }

    fun readCurrency(name: String): CurrenciesEntity {
        return currencyDao.readCurrency(name)
    }

    fun deleteAllCurrencies() {
        currencyDao.deleteAllCurrencies()
    }
}

