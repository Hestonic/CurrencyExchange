package com.example.exchanger.data.repository

import com.example.exchanger.data.mapper.HistoryDtoMapperImpl
import com.example.exchanger.data.sources.LocalDataSource
import com.example.exchanger.domain.model.HistoryDtoModel
import com.example.exchanger.domain.repository.HistoryRepository
import java.time.LocalDateTime

class HistoryRepositoryImpl(private val localDataSource: LocalDataSource) : HistoryRepository {

    override suspend fun addHistoryItem(historyDomainModel: HistoryDtoModel) {
        localDataSource.addHistoryItem(
            HistoryDtoMapperImpl.mapHistoryDomainModelToEntity(
                historyDomainModel
            )
        )
    }
    
    override fun getHistory(): List<HistoryDtoModel> =
        HistoryDtoMapperImpl.mapHistoryEntityToDomainModel(localDataSource.readAllHistory())
    
    override fun searchDateHistory(
        dateFrom: LocalDateTime, dateTo: LocalDateTime
    ): List<HistoryDtoModel> = HistoryDtoMapperImpl.mapHistoryEntityToDomainModel(
        localDataSource.searchDateHistory(dateFrom, dateTo)
    )
    
    override fun searchCurrenciesHistory(currency: String): List<HistoryDtoModel> =
        HistoryDtoMapperImpl.mapHistoryEntityToDomainModel(
            localDataSource.searchCurrenciesHistory(currency)
        )
}