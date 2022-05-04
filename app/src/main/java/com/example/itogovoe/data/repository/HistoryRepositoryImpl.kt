package com.example.itogovoe.data.repository

import com.example.itogovoe.data.sources.LocalDataSource
import com.example.itogovoe.data.sources.RemoteDataSource
import com.example.itogovoe.domain.mapper.HistoryDtoMapper
import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.domain.repository.HistoryRepository
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import java.time.LocalDateTime

class HistoryRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : HistoryRepository {

    override suspend fun addHistoryItem(historyDomainModel: HistoryDtoModel) {
        localDataSource.addHistoryItem(
            HistoryUiModelMapper.mapHistoryDomainModelToEntity(
                historyDomainModel
            )
        )
    }

    override fun getHistory(): List<HistoryDtoModel> =
        HistoryDtoMapper.mapHistoryEntityToDomainModel(localDataSource.readAllHistory())


    override fun searchDateHistory(
        dateFrom: LocalDateTime,
        dateTo: LocalDateTime
    ): List<HistoryDtoModel> = HistoryDtoMapper.mapHistoryEntityToDomainModel(
        localDataSource.searchDateHistory(dateFrom, dateTo)
    )

}