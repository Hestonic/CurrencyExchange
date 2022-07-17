package com.example.itogovoe.domain.repository

import com.example.itogovoe.domain.model.CurrencyDtoModel

interface CurrencyRepository {
    suspend fun getCurrencies(): List<CurrencyDtoModel>
    suspend fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean)
    suspend fun updateCurrencyLastUsedAt(name: String)
    fun isFresh(): Boolean
    fun searchCurrenciesDatabase(searchQuery: String): List<CurrencyDtoModel>
    fun readCurrency(name: String): CurrencyDtoModel
}