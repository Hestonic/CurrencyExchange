package com.example.exchanger.domain.mapper

import com.example.exchanger.data.sources.local_source.entities.CurrenciesEntity
import com.example.exchanger.data.sources.local_source.entities.HistoryEntity
import com.example.exchanger.domain.model.CurrencyDtoModel
import com.example.exchanger.domain.model.HistoryDtoModel

interface HistoryDtoMapper {
    fun mapHistoryEntityToDomainModel(history: List<HistoryEntity>): List<HistoryDtoModel>
    fun mapHistoryDomainModelToEntity(historyDomainModel: HistoryDtoModel): HistoryEntity
    fun mapCurrenciesEntityNotFreshToFresh(
        localCurrencyNotFresh: CurrenciesEntity,
        currencyDtoModel: CurrencyDtoModel
    ): CurrenciesEntity
}