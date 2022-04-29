package com.example.itogovoe.data.sources

import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.dao.HistoryDao
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.model.CurrencyDtoModel
import java.time.LocalDateTime

class LocalDataSource(private val currencyDao: CurrencyDao, private val historyDao: HistoryDao) {

    //HistoryEntity
    suspend fun addHistoryItem(history: HistoryEntity) {
        historyDao.addHistoryItem(history)
    }

    fun readAllHistory(): List<HistoryEntity> {
        return historyDao.readAllHistory()
    }

    /* TODO: Filter
    fun searchDateHistory(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<HistoryEntity> {
        val dateFromTimestamp = DateConverter().localDateTimeToTimestamp(dateFrom)
        val dateToTimestamp = DateConverter().localDateTimeToTimestamp(dateTo)
        Log.d("date_timestamp_tag", "dateFrom $dateFromTimestamp, dateTo $dateToTimestamp")
        return currencyDao.searchDateHistory(dateFromTimestamp, dateToTimestamp)
    }*/


    // CurrenciesEntity
    suspend fun addCurrencyItem(currencies: CurrenciesEntity) {
        currencyDao.addCurrencyItem(currencies)
    }

    fun readAllCurrencies(): List<CurrenciesEntity> {
        return currencyDao.readAllCurrencies()
    }

    suspend fun updateCurrency(currencyDtoModel: CurrencyDtoModel) {
        val localCurrencyNotFresh = readCurrency(currencyDtoModel.name)
        currencyDao.updateCurrency(
            CurrenciesEntity(
                name = currencyDtoModel.name,
                value = currencyDtoModel.value,
                createdAt = localCurrencyNotFresh.createdAt,
                updatedAt = LocalDateTime.now(),
                lastUsedAt = localCurrencyNotFresh.lastUsedAt,
                isFavourite = localCurrencyNotFresh.isFavourite
            )
        )


    }

    suspend fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        val currencyFromDb = readCurrency(name)
        currencyDao.updateCurrency(
            CurrenciesEntity(
                name = name,
                value = currencyFromDb.value,
                createdAt = currencyFromDb.createdAt,
                updatedAt = currencyFromDb.updatedAt,
                lastUsedAt = currencyFromDb.lastUsedAt,
                isFavourite = isFavourite
            )
        )
    }

    suspend fun updateCurrencyLastUsedAt(name: String) {
        val currencyFromDb = readCurrency(name)
        currencyDao.updateCurrency(
            CurrenciesEntity(
                name = name,
                value = currencyFromDb.value,
                createdAt = currencyFromDb.createdAt,
                updatedAt = currencyFromDb.updatedAt,
                lastUsedAt = LocalDateTime.now(),
                isFavourite = currencyFromDb.isFavourite
            )
        )
    }

    fun readCurrency(name: String): CurrenciesEntity {
        return currencyDao.readCurrency(name)
    }
}

