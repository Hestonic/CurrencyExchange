package com.example.itogovoe.domain.repository

import com.example.itogovoe.data.api.RetrofitInstance
import com.example.itogovoe.data.api.Currency
import com.example.itogovoe.data.source.LocalDataSource
import com.example.itogovoe.data.source.RemoteDataSource
import retrofit2.Response
import java.lang.Exception

class Repository(
    private val localDateSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCurrencies(): Response<Currency> {
        return RetrofitInstance.repository.remoteDataSource.getCurrencies()

        /*данные могут быть "свежие" - 5 минут*/
        /*Данные могут быть "свежие" - 5 минут
        * Данные "несвежие" (протухшие), то идём в сеть и сохраняем обновлённые
        * если "свежие" - возвращаем из БД*/

        // Смотрим на свежесть (псевдокод):
        /*val localData = localDataSource.getCurrentData()
        if (localData.isFresh) {
            return localData
        } else {
            val currencies = remoteDataSource.getCurrencies()
            localDateSource.saveCurrencies(currencies)
            return currencies
        }*/

        // Маппинг данных
        // Заменить getCurrencies(): Response<Currency> -> getCurrencies(): Currencies?
        /*return try {
            val response = remoteDataSource.getCurrencies()
            CurrencyDtoMapper.mapResponseToDomainModel(response)
        } catch (e: Exception) {
            null
        }*/
    }
}