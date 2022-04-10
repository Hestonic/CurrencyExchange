package com.example.itogovoe

import android.content.Context
import com.example.itogovoe.data.api.CurrencyApi
import com.example.itogovoe.data.source.LocalDataSource
import com.example.itogovoe.data.source.RemoteDataSource
import com.example.itogovoe.data.source.local_source.dao.CurrencyDao
import com.example.itogovoe.data.source.local_source.database.CurrencyDatabase
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DependencyInjection {

    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: CurrencyApi by lazy {
        retrofit.create(CurrencyApi::class.java)
    }

    val remoteDataSource = RemoteDataSource(api)
    lateinit var currencyDao: CurrencyDao
    lateinit var localDataSource: LocalDataSource
    lateinit var repository: Repository

    fun initCurrencyDao(context: Context) {
        currencyDao = CurrencyDatabase.getDatabase(context).currencyDao
    }

    fun initLocal(currencyDao: CurrencyDao) {
        localDataSource = LocalDataSource(currencyDao)
    }

    fun initRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource) {
        repository = Repository(localDataSource, remoteDataSource)
    }
}