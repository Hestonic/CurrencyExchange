package com.example.exchanger.data.sources

import com.example.exchanger.data.mapper.HistoryDtoMapperImpl
import com.example.exchanger.data.sources.local_source.DateConverter
import com.example.exchanger.data.sources.local_source.dao.CurrencyDao
import com.example.exchanger.data.sources.local_source.dao.HistoryDao
import com.example.exchanger.data.sources.local_source.entities.CurrenciesEntity
import com.example.exchanger.data.sources.local_source.entities.HistoryEntity
import com.example.exchanger.domain.model.CurrencyDtoModel
import java.time.LocalDateTime

class LocalDataSource(private val currencyDao: CurrencyDao, private val historyDao: HistoryDao) {

    //HistoryEntity
    fun addHistoryItem(history: HistoryEntity) {
        historyDao.addHistoryItem(history)
    }
    
    fun readAllHistory(): List<HistoryEntity> {
        return historyDao.readAllHistory()
    }
    
    fun searchDateHistory(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<HistoryEntity> {
        val dateFromTimestamp = DateConverter().localDateTimeToTimestamp(dateFrom)
        val dateToTimestamp = DateConverter().localDateTimeToTimestamp(dateTo)
        return historyDao.searchDateHistory(dateFromTimestamp, dateToTimestamp)
    }
    
    fun searchCurrenciesHistory(currency: String): List<HistoryEntity> {
        return historyDao.searchCurrenciesHistory(currency)
    }
    
    
    // CurrenciesEntity
    fun addCurrencyItem(currencies: CurrenciesEntity) {
        currencyDao.addCurrencyItem(currencies)
    }
    
    fun searchCurrenciesDatabase(searchQuery: String) =
        currencyDao.searchCurrenciesDatabase(searchQuery)


    fun readAllCurrencies(): List<CurrenciesEntity> {
        return currencyDao.readAllCurrencies()
    }

    fun updateCurrency(currencyDtoModel: CurrencyDtoModel) {
        val localCurrencyNotFresh = readCurrency(currencyDtoModel.name)
        val localCurrencyFresh =
            HistoryDtoMapperImpl.mapCurrenciesEntityNotFreshToFresh(localCurrencyNotFresh, currencyDtoModel)
        currencyDao.updateCurrency(localCurrencyFresh)
    }

    fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        val localCurrency = readCurrency(name)
        currencyDao.updateCurrency(
            localCurrency.copy(
                isFavourite = isFavourite,
                lastUsedAt = LocalDateTime.now()
            )
        )
    }
    
    fun updateCurrencyLastUsedAt(name: String) {
        val localCurrency = readCurrency(name)
        currencyDao.updateCurrency(localCurrency.copy(lastUsedAt = LocalDateTime.now()))
    }
    
    fun updateCurrencyIsChecked(name: String) {
        val localCurrency = readCurrency(name)
        currencyDao.updateCurrency(
            localCurrency.copy(
                isChecked = !localCurrency.isChecked,
                lastUsedAt = LocalDateTime.now()
            )
        )
    }
    
    fun readCurrency(name: String): CurrenciesEntity {
        return currencyDao.readCurrency(name)
    }
}

