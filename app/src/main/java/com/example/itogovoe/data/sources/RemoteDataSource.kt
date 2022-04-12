package com.example.itogovoe.data.sources

import com.example.itogovoe.data.api.CurrencyResponse
import retrofit2.Response
import com.example.itogovoe.data.api.CurrencyApi

class RemoteDataSource(private val currencyApi: CurrencyApi) {
    suspend fun getCurrencies(): Response<CurrencyResponse> {
        return currencyApi.getCurrencies()
    }
}