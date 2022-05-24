package com.example.itogovoe.data.repository

import android.util.Log
import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.data.mapper.CurrencyDtoMapperImpl
import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.data.sources.RemoteDataSource
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.domain.model.CurrencyDtoModel
import com.example.itogovoe.domain.repository.CurrencyRepository
import retrofit2.Response
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class CurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : CurrencyRepository {

    override suspend fun getCurrencies(): List<CurrencyDtoModel> {
        try {
            val localCurrencies = localDataSource.readAllCurrencies()
            Log.d("REPOSITORY_TAG", "localCurrencies: ${localCurrencies.size}")
            return when {
                localCurrencies.isEmpty() -> saveResponseAndReturnUpdatedLocalCurrencies()
                isFresh() -> {
                    Log.d("REPOSITORY_TAG", "Data is fresh")
                    getLocalCurrencies()
                }
                else -> updateAndReturnUpdatedLocalCurrencies()

            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    private suspend fun updateAndReturnUpdatedLocalCurrencies(): List<CurrencyDtoModel> {
        Log.d("REPOSITORY_TAG", "Data is not fresh")
        val remoteCurrency = remoteDataSource.getCurrencies()
        return if (remoteCurrency.isSuccessful) {
            updateCurrenciesData(remoteCurrency)
            getLocalCurrencies()
        } else {
            Log.d("CurrencyRepository_response_error_on_update", remoteCurrency.errorBody().toString())
            emptyList()
        }
    }

    private suspend fun saveResponseAndReturnUpdatedLocalCurrencies(): List<CurrencyDtoModel> {
        Log.d("REPOSITORY_TAG", "Database is empty")
        val remoteCurrency = remoteDataSource.getCurrencies()
        return if (remoteCurrency.isSuccessful) {
            saveCurrencies(remoteCurrency)
            getLocalCurrencies()
        } else {
            Log.d("CurrencyRepository_response_error_on_save", remoteCurrency.errorBody().toString())
            emptyList()
        }
    }

    private suspend fun saveCurrencies(remoteCurrency: Response<CurrencyResponse>) {
        val currencyDtoModelList =
            CurrencyDtoMapperImpl.mapCurrencyResponseToCurrencyDomainModelList(remoteCurrency)
        val listCurrenciesTable =
            CurrencyDtoMapperImpl.mapCurrencyDtoModelListToCurrenciesEntityList(currencyDtoModelList)
        listCurrenciesTable.forEach { localDataSource.addCurrencyItem(it) }
        Log.d("REPOSITORY_TAG", "Data has been added to database")
    }

    private suspend fun updateCurrenciesData(remoteCurrency: Response<CurrencyResponse>) {
        val currencyDtoModelList =
            CurrencyDtoMapperImpl.mapCurrencyResponseToCurrencyDomainModelList(remoteCurrency)
        currencyDtoModelList.forEach { localDataSource.updateCurrency(it) }
        Log.d("REPOSITORY_TAG", "Database has been updated")
    }

    override fun isFresh(): Boolean {
        val localCurrencies = localDataSource.readAllCurrencies()
        val dateNow = LocalDateTime.now()
        var minutes: Long = 0
        // TODO: а если localCurrencies.size = 0?
        if (localCurrencies.isNotEmpty())
            minutes = ChronoUnit.MINUTES.between(localCurrencies[0].updatedAt, dateNow)
        Log.d("difference_date", minutes.toString())
        return minutes < 6
    }

    private fun getLocalCurrencies() =
        CurrencyDtoMapperImpl.mapListCurrenciesEntityToDomainModelList(readAllCurrencies())

    private fun readAllCurrencies(): List<CurrenciesEntity> = localDataSource.readAllCurrencies()


    override suspend fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        localDataSource.updateCurrencyIsFavourite(name, isFavourite)
    }

    override suspend fun updateCurrencyLastUsedAt(name: String) {
        localDataSource.updateCurrencyLastUsedAt(name)
    }

    override fun searchCurrenciesDatabase(searchQuery: String) =
        CurrencyDtoMapperImpl.mapListCurrenciesEntityToDomainModelList(
            localDataSource.searchCurrenciesDatabase(searchQuery)
        )

    override fun readCurrency(name: String): CurrencyDtoModel =
        CurrencyDtoMapperImpl.mapCurrencyEntityToDomainModel(localDataSource.readCurrency(name))

}