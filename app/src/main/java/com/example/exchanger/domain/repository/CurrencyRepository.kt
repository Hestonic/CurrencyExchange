package com.example.exchanger.domain.repository

import com.example.exchanger.domain.model.CurrencyDtoModel

interface CurrencyRepository {
    suspend fun getCurrencies(): List<CurrencyDtoModel>
    suspend fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean)
    suspend fun updateCurrencyLastUsedAt(name: String)
    suspend fun updateCurrencyIsChecked(name: String)
    fun isFresh(): Boolean
    fun searchCurrenciesDatabase(searchQuery: String): List<CurrencyDtoModel>
    fun readCurrency(name: String): CurrencyDtoModel
}