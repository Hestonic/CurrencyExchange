package com.example.itogovoe.domain.mapper

import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.model.HistoryDomainModel

object HistoryDtoMapper {
    fun mapHistoryEntityToDomainModel(history: List<HistoryEntity>): List<HistoryDomainModel> {
        return history.map {
            HistoryDomainModel(
                date = it.date,
                currencyNameParent = it.currencyNameParent,
                currencyValueParent = it.currencyValueParent,
                currencyNameChild = it.currencyNameChild,
                currencyValueChild = it.currencyValueChild,
            )
        }
    }
}