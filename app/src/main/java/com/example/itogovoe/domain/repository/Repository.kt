package com.example.itogovoe.domain.repository

import android.util.Log
import com.example.itogovoe.data.source.LocalDataSource
import com.example.itogovoe.data.source.RemoteDataSource
import com.example.itogovoe.domain.mapper.CurrencyDtoMapper
import com.example.itogovoe.domain.model.Currencies
import java.time.LocalDateTime
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.*

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCurrencies(): Currencies? {
        try {
            // Получаем данные из сети
            val localCurrencies = localDataSource.readAllCurrencies()
            val response = remoteDataSource.getCurrencies()
            val domainModel = CurrencyDtoMapper.mapResponseToDomainModel(response)
            // Если БД пустая - берём данные из сети и загружаем в БД
            if (localCurrencies.isEmpty()) {
                val currenciesEntity =
                    CurrencyDtoMapper.mapDomainModelToCurrenciesEntity(domainModel)
                currenciesEntity?.forEach { localDataSource.addCurrencyItem(it) }
                val infoEntity = CurrencyDtoMapper.mapDomainModelToInfoEntity(domainModel)
                localDataSource.addInfo(infoEntity)
                Log.d("repository_return_tag", "добавляем данные в БД")
            } else if (localCurrencies.isNotEmpty()) {
                val infoEntity = localDataSource.readAllInfo()
                val dateNow = LocalDateTime.now()
                val minutes = ChronoUnit.MINUTES.between(infoEntity.lastUploadDate, dateNow)
                Log.d("difference_date", minutes.toString())
                // Проверяем данные на "старость"
                return if (minutes < 5) {
                    Log.d("repository_return_tag", "вернули старые данные")
                    val currenciesEntity =
                        CurrencyDtoMapper.mapDomainModelToCurrenciesEntity(domainModel)
                    CurrencyDtoMapper.mapCurrenciesEntityToDomainModel(currenciesEntity, infoEntity)
                } else {
                    Log.d("repository_return_tag", "вернули новые данные")
                    localDataSource.deleteAllCurrencies()
                    localDataSource.deleteAllInfo()
                    val infoEntity = CurrencyDtoMapper.mapDomainModelToInfoEntity(domainModel)
                    val currenciesEntity =
                        CurrencyDtoMapper.mapDomainModelToCurrenciesEntity(domainModel)
                    currenciesEntity?.forEach { localDataSource.addCurrencyItem(it) }
                    localDataSource.addInfo(infoEntity)
                    domainModel
                }
            }
            return domainModel
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}