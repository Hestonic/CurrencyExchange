package com.example.exchanger.data.sources

import com.example.exchanger.data.api.CurrencyApi
import com.example.exchanger.data.api.CurrencyResponse
import retrofit2.Response

class RemoteDataSource(private val currencyApi: CurrencyApi) {
    suspend fun getCurrencies(): Response<CurrencyResponse> {
        return currencyApi.getCurrencies()
    }
}