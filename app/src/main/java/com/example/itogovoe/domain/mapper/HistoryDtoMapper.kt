package com.example.itogovoe.domain.mapper

import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.model.CurrencyDtoModel
import com.example.itogovoe.domain.model.HistoryDtoModel

interface HistoryDtoMapper {
    fun mapHistoryEntityToDomainModel(history: List<HistoryEntity>): List<HistoryDtoModel>
    fun mapHistoryDomainModelToEntity(historyDomainModel: HistoryDtoModel): HistoryEntity
    fun mapCurrenciesEntityNotFreshToFresh(
        localCurrencyNotFresh: CurrenciesEntity,
        currencyDtoModel: CurrencyDtoModel
    ): CurrenciesEntity
}