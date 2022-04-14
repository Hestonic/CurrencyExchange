package com.example.itogovoe.domain.repository

import android.util.Log
import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.data.sources.RemoteDataSource
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity
import com.example.itogovoe.domain.mapper.CurrencyDtoMapper
import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.domain.model.HistoryDomainModel
import retrofit2.Response
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {


    private lateinit var response: Response<CurrencyResponse>
    private lateinit var domainModel: Currencies
    private lateinit var infoEntity: InfoEntity

    suspend fun getCurrencies(): Currencies? {
        try {
            // Получаем данные из локального хранилища
            /*localDataSource.deleteAllCurrencies()
            localDataSource.deleteAllInfo()
            localDataSource.deleteAllHistory()*/
            val localCurrencies = localDataSource.readAllCurrencies()
            // Если БД пустая - берём данные из сети и загружаем в БД
            if (localCurrencies.isEmpty()) {
                response = remoteDataSource.getCurrencies()
                domainModel = CurrencyDtoMapper.mapResponseToDomainModel(response)
                val currenciesEntity =
                    CurrencyDtoMapper.mapDomainModelToCurrenciesEntity(domainModel)
                currenciesEntity?.forEach { localDataSource.addCurrencyItem(it) }
                infoEntity = CurrencyDtoMapper.mapDomainModelToInfoEntity(domainModel)
                localDataSource.addInfo(infoEntity)
                Log.d("repository_return_tag", "добавляем данные в БД")
            } else if (localCurrencies.isNotEmpty()) {
                // Проверяем данные на "старость"
                return if (isFresh()) {
                    Log.d("repository_return_tag", "вернули локальные данные")
                    CurrencyDtoMapper.mapCurrenciesEntityToDomainModel(localCurrencies, infoEntity)
                } else {
                    Log.d("repository_return_tag", "вернули данные из сети")
                    response = remoteDataSource.getCurrencies()
                    domainModel = CurrencyDtoMapper.mapResponseToDomainModel(response)
                    // удалили старые данные
                    localDataSource.deleteAllCurrencies()
                    localDataSource.deleteAllInfo()
                    // добавили новые
                    infoEntity = CurrencyDtoMapper.mapDomainModelToInfoEntity(domainModel)
                    val currenciesEntity =
                        CurrencyDtoMapper.mapDomainModelToCurrenciesEntity(domainModel)
                    currenciesEntity?.forEach { localDataSource.addCurrencyItem(it) }
                    localDataSource.addInfo(infoEntity)
                    // вернули актуальные данные
                    domainModel
                }
            }
            response = remoteDataSource.getCurrencies()
            return CurrencyDtoMapper.mapResponseToDomainModel(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun isFresh(): Boolean {
        infoEntity = localDataSource.readAllInfo()
        val dateNow = LocalDateTime.now()
        val minutes = ChronoUnit.MINUTES.between(infoEntity.lastUploadDate, dateNow)
        Log.d("difference_date", minutes.toString())
        return minutes < 6
    }

    suspend fun addHistoryItem(historyEntity: HistoryEntity) {
        localDataSource.addHistoryItem(historyEntity)
    }

    fun getHistory(): List<HistoryDomainModel> {
        return CurrencyDtoMapper.mapHistoryEntityToDomainModel(localDataSource.readAllHistory())
    }

    /*fun deleteCurrencyUiItem(name: String) {
        localDataSource.deleteCurrencyUiItem(name)
    }*/
}