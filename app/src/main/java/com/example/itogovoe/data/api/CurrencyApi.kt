package com.example.itogovoe.data.api

import retrofit2.Response
import retrofit2.http.GET


interface CurrencyApi {
//    @GET("/api/latest?access_key=2d63a81787b970f276c524344fb42f7f&format=1")
    @GET("/db.json")
    suspend fun getCurrencies(): Response<CurrencyResponse>
}