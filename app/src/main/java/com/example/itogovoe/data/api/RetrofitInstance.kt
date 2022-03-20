package com.example.itogovoe.data.api

import com.example.itogovoe.data.source.LocalDataSource
import com.example.itogovoe.data.source.RemoteDataSource
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: CurrencyApi by lazy {
        retrofit.create(CurrencyApi::class.java)
    }

    private val localDataSource = LocalDataSource()
    private val remoteDataSource = RemoteDataSource(api)
    val repository = Repository(localDataSource, remoteDataSource)

}