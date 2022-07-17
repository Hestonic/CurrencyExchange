package com.example.itogovoe.data.sources

import com.example.itogovoe.data.api.CurrencyApi
import com.example.itogovoe.data.api.CurrencyResponse
import retrofit2.Response

class RemoteDataSource(private val currencyApi: CurrencyApi) {
    suspend fun getCurrencies(): Response<CurrencyResponse> {
        return currencyApi.getCurrencies()
    }
}