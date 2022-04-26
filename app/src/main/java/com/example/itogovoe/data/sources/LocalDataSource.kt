package com.example.itogovoe.data.sources

import android.util.Log
import com.example.itogovoe.data.sources.local_source.converters.DateConverter
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity
import java.time.LocalDateTime

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

    fun searchDateHistory(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<HistoryEntity> {
        val dateFromTimestamp = DateConverter().localDateTimeToTimestamp(dateFrom)
        val dateToTimestamp = DateConverter().localDateTimeToTimestamp(dateTo)
        Log.d("date_timestamp_tag", "dateFrom $dateFromTimestamp, dateTo $dateToTimestamp")
        return currencyDao.searchDateHistory(dateFromTimestamp, dateToTimestamp)
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
}

