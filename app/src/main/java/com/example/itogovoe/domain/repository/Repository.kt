package com.example.itogovoe.domain.repository

import android.util.Log
import com.example.itogovoe.data.source.LocalDataSource
import com.example.itogovoe.data.source.RemoteDataSource
import com.example.itogovoe.domain.mapper.CurrencyDtoMapper
import com.example.itogovoe.domain.model.Currencies

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCurrencies(): Currencies? {
/*
* Проверяем пустая ли БД
* Если БД пустая - берём данные из сети и загружаем в БД currencies
* Иначе берём данные из БД и выгружаем их, смотрим на дату данных
* Если данные старые, то идём в сеть
* Загружаем полученные данные в бд и возвращаем пользователю данные из сети
* Иначе возвращаем данные из Бд */
        try {
            val response = remoteDataSource.getCurrencies()
            val domainModel = CurrencyDtoMapper.mapResponseToDomainModel(response)
            val localCurrencies = localDataSource.readAllCurrencies()
            val currenciesEntity = CurrencyDtoMapper.mapDomainModelToCurrenciesEntity(domainModel)
            if (localCurrencies.isEmpty()) {
                Log.d("MY_LOCAL_TAG 2", localCurrencies.toString())
                currenciesEntity?.forEach { currency ->
                    localDataSource.addCurrencyItem(currency)
                }
            }
            //localDataSource.deleteAllCurrencies()

            /*
            Log.d("MY_LOCAL_TAG 1", currenciesEntity.toString())

            localDataSource.deleteAllCurrencies()

            Log.d("MY_LOCAL_TAG 2", localCurrencies.toString())
            val infoEntity = CurrencyDtoMapper.mapDomainModelToInfoEntity(domainModel)
            localDataSource.addInfo(infoEntity)*/
            return domainModel
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}