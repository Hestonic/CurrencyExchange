package com.example.itogovoe.data.source

import com.example.itogovoe.data.api.Currency
import retrofit2.Response
import com.example.itogovoe.data.api.CurrencyApi

class RemoteDataSource(private val currencyApi: CurrencyApi) {

    suspend fun getCurrencies(): Response<Currency> {
        return currencyApi.getCurrencies()
    }
}