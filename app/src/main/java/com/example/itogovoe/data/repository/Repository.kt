package com.example.itogovoe.data.repository

import com.example.itogovoe.data.api.RetrofitInstance
import com.example.itogovoe.model.Currency
import retrofit2.Response

class Repository {

    suspend fun getCurrency(): Response<Currency> {
        return RetrofitInstance.api.getCurrencies()
    }
}