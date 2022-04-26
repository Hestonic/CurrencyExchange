package com.example.itogovoe.domain.repository

import android.util.Log
import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.data.sources.RemoteDataSource
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.mapper.CurrencyDtoMapper
import com.example.itogovoe.domain.mapper.HistoryDtoMapper
import com.example.itogovoe.domain.model.CurrencyDtoModel
import com.example.itogovoe.domain.model.HistoryDomainModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    // TODO: Разбить getCurrencies() на более мелкие функции
    suspend fun getCurrencies(): List<CurrencyDtoModel>? {
        try {
            val localCurrencies = localDataSource.readAllCurrencies()
            Log.d("REPOSITORY_TAG", "localCurrencies: ${localCurrencies.size}")
            return when {
                localCurrencies.isEmpty() -> {
                    Log.d("REPOSITORY_TAG", "Database is empty")
                    saveCurrencies()
                    getLocalCurrencies()
                }
                isFresh() -> {
                    Log.d("REPOSITORY_TAG", "Data is fresh")
                    getLocalCurrencies()
                }
                else -> {
                    Log.d("REPOSITORY_TAG", "Data is not fresh")
                    updateCurrenciesData()
                    getLocalCurrencies()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private suspend fun saveCurrencies() {
        val remoteCurrencyDtoModel =
            CurrencyDtoMapper.mapCurrencyResponseToCurrencyDomainModel(remoteDataSource.getCurrencies())
        val listCurrenciesTable =
            CurrencyDtoMapper.mapCurrencyDtoModelToCurrenciesEntityList(remoteCurrencyDtoModel)
        listCurrenciesTable?.forEach { localDataSource.addCurrencyItem(it) }
        Log.d("REPOSITORY_TAG", "Data has been added to database")
    }

    private fun getLocalCurrencies() =
        CurrencyDtoMapper.mapCurrenciesEntityToDomainModel(readAllCurrencies())

    private fun readAllCurrencies(): List<CurrenciesEntity> = localDataSource.readAllCurrencies()

    private suspend fun updateCurrency(currenciesTable: CurrenciesEntity) {
        localDataSource.updateCurrency(currenciesTable)
    }

    private suspend fun updateCurrenciesData() {
        val localCurrenciesNotFresh = localDataSource.readAllCurrencies()
        val remoteCurrency =
            CurrencyDtoMapper.mapCurrencyResponseToCurrencyDomainModel(remoteDataSource.getCurrencies())
        remoteCurrency?.forEachIndexed { i, remoteCurrencies ->
            updateCurrency(
                CurrenciesEntity(
                    // TODO: Избавиться от !!
                    name = remoteCurrencies.name!!,
                    value = remoteCurrencies.value!!,
                    createdAt = localCurrenciesNotFresh[i].createdAt,
                    updatedAt = LocalDateTime.now(),
                    lastUsedAt = localCurrenciesNotFresh[i].lastUsedAt,
                    isFavourite = localCurrenciesNotFresh[i].isFavourite
                )
            )
        }
        Log.d("REPOSITORY_TAG", "Database has been updated")
    }


    fun isFresh(): Boolean {
        val localCurrencies = localDataSource.readAllCurrencies()
        val dateNow = LocalDateTime.now()
        val minutes = ChronoUnit.MINUTES.between(localCurrencies[0].updatedAt, dateNow)
        Log.d("difference_date", minutes.toString())
        return minutes < 6
    }



    //History
    suspend fun addHistoryItem(historyEntity: HistoryEntity) {
        localDataSource.addHistoryItem(historyEntity)
    }

    fun getHistory(): List<HistoryDomainModel> {
        return HistoryDtoMapper.mapHistoryEntityToDomainModel(localDataSource.readAllHistory())
    }

    fun searchDateHistory(
        dateFrom: LocalDateTime,
        dateTo: LocalDateTime
    ): List<HistoryDomainModel> {
        return HistoryDtoMapper.mapHistoryEntityToDomainModel(
            localDataSource.searchDateHistory(dateFrom, dateTo)
        )
    }

    /*fun searchDateHistory(query: SupportSQLiteQuery): LiveData<List<HistoryEntity>> {
        return localDataSource.searchDateHistory(query)
    }*/
}