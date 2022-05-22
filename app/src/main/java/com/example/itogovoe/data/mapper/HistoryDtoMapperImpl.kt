package com.example.itogovoe.data.mapper

import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.mapper.HistoryDtoMapper
import com.example.itogovoe.domain.model.CurrencyDtoModel
import com.example.itogovoe.domain.model.HistoryDtoModel
import java.time.LocalDateTime

object HistoryDtoMapperImpl : HistoryDtoMapper {

    override fun mapHistoryEntityToDomainModel(history: List<HistoryEntity>): List<HistoryDtoModel> {
        return history.map {
            HistoryDtoModel(
                date = it.date,
                currencyNameParent = it.currencyNameParent,
                currencyValueParent = it.currencyValueParent,
                currencyNameChild = it.currencyNameChild,
                currencyValueChild = it.currencyValueChild,
            )
        }
    }

    override fun mapHistoryDomainModelToEntity(historyDomainModel: HistoryDtoModel): HistoryEntity {
        return HistoryEntity(
            id = 0,
            currencyNameParent = historyDomainModel.currencyNameParent,
            currencyValueParent = historyDomainModel.currencyValueParent,
            currencyNameChild = historyDomainModel.currencyNameChild,
            currencyValueChild = historyDomainModel.currencyValueChild,
            date = historyDomainModel.date
        )
    }

    override fun mapCurrenciesEntityNotFreshToFresh(
        localCurrencyNotFresh: CurrenciesEntity,
        currencyDtoModel: CurrencyDtoModel
    ): CurrenciesEntity {
        return CurrenciesEntity(
            name = currencyDtoModel.name,
            value = currencyDtoModel.value,
            createdAt = localCurrencyNotFresh.createdAt,
            updatedAt = LocalDateTime.now(),
            lastUsedAt = localCurrencyNotFresh.lastUsedAt,
            isFavourite = localCurrencyNotFresh.isFavourite
        )
    }
}