package com.example.itogovoe.domain.repository

import android.util.Log
import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.data.sources.RemoteDataSource
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.domain.mapper.CurrencyDtoMapper
import com.example.itogovoe.domain.mapper.HistoryDtoMapper
import com.example.itogovoe.domain.model.CurrencyDtoModel
import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import retrofit2.Response
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCurrencies(): List<CurrencyDtoModel>? {
        try {
            val localCurrencies = localDataSource.readAllCurrencies()
            Log.d("REPOSITORY_TAG", "localCurrencies: ${localCurrencies.size}")
            return when {
                localCurrencies.isEmpty() -> {
                    Log.d("REPOSITORY_TAG", "Database is empty")
                    val remoteCurrency = remoteDataSource.getCurrencies()
                    return if (remoteCurrency.isSuccessful) {
                        saveCurrencies(remoteCurrency)
                        getLocalCurrencies()
                    } else null
                }
                isFresh() -> {
                    Log.d("REPOSITORY_TAG", "Data is fresh")
                    getLocalCurrencies()
                }
                else -> {
                    Log.d("REPOSITORY_TAG", "Data is not fresh")
                    val remoteCurrency = remoteDataSource.getCurrencies()
                    return if (remoteCurrency.isSuccessful) {
                        updateCurrenciesData(remoteCurrency)
                        getLocalCurrencies()
                    } else null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private suspend fun saveCurrencies(remoteCurrency: Response<CurrencyResponse>) {
        val currencyDtoModelList =
            CurrencyDtoMapper.mapCurrencyResponseToCurrencyDomainModelList(remoteCurrency)
        val listCurrenciesTable =
            CurrencyDtoMapper.mapCurrencyDtoModelListToCurrenciesEntityList(currencyDtoModelList)
        listCurrenciesTable?.forEach { localDataSource.addCurrencyItem(it) }
        Log.d("REPOSITORY_TAG", "Data has been added to database")
    }

    private fun getLocalCurrencies() =
        CurrencyDtoMapper.mapListCurrenciesEntityToDomainModelList(readAllCurrencies())

    private fun readAllCurrencies(): List<CurrenciesEntity> = localDataSource.readAllCurrencies()

    suspend fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        localDataSource.updateCurrencyIsFavourite(name, isFavourite)
    }

    suspend fun updateCurrencyLastUsedAt(name: String) {
        localDataSource.updateCurrencyLastUsedAt(name)
    }

    fun readCurrency(name: String): CurrencyDtoModel =
        CurrencyDtoMapper.mapCurrencyEntityToDomainModel(localDataSource.readCurrency(name))

    private suspend fun updateCurrenciesData(remoteCurrency: Response<CurrencyResponse>) {
        val currencyDtoModelList =
            CurrencyDtoMapper.mapCurrencyResponseToCurrencyDomainModelList(remoteCurrency)
        currencyDtoModelList.forEach { localDataSource.updateCurrency(it) }
        Log.d("REPOSITORY_TAG", "Database has been updated")
    }

    fun isFresh(): Boolean {
        val localCurrencies = localDataSource.readAllCurrencies()
        val dateNow = LocalDateTime.now()
        val minutes = ChronoUnit.MINUTES.between(localCurrencies[0].updatedAt, dateNow)
        Log.d("difference_date", minutes.toString())
        return minutes < 1
    }

    fun searchCurrenciesDatabase(searchQuery: String) =
        CurrencyDtoMapper.mapListCurrenciesEntityToDomainModelList(
            localDataSource.searchCurrenciesDatabase(searchQuery)
        )


    //History
    suspend fun addHistoryItem(historyDomainModel: HistoryDtoModel) {
        localDataSource.addHistoryItem(
            HistoryUiModelMapper.mapHistoryDomainModelToEntity(
                historyDomainModel
            )
        )
    }

    fun getHistory(): List<HistoryDtoModel> {
        return HistoryDtoMapper.mapHistoryEntityToDomainModel(localDataSource.readAllHistory())
    }

    fun searchDateHistory(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<HistoryDtoModel> {
        return HistoryDtoMapper.mapHistoryEntityToDomainModel(
            localDataSource.searchDateHistory(dateFrom, dateTo)
        )
    }
}