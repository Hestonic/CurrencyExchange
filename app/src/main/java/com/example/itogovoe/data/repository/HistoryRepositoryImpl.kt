package com.example.itogovoe.data.repository

import com.example.itogovoe.data.mapper.HistoryDtoMapperImpl
import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.domain.repository.HistoryRepository
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
        dateFrom: LocalDateTime,
        dateTo: LocalDateTime
    ): List<HistoryDtoModel> = HistoryDtoMapperImpl.mapHistoryEntityToDomainModel(
        localDataSource.searchDateHistory(dateFrom, dateTo)
    )

}