package com.example.itogovoe.ui.mapper

import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.model.HistoryUiModel
import java.time.LocalDateTime

object HistoryUiModelMapper {

    fun mapHistoryEntityToUiModel(history: List<HistoryDtoModel>): List<HistoryUiModel> {
        return history.map {
            HistoryUiModel(
                date = it.date.toString().replace("T", " "),
                currencyNameParent = it.currencyNameParent,
                currencyValueParent = it.currencyValueParent,
                currencyNameChild = it.currencyNameChild,
                currencyValueChild = it.currencyValueChild,
            )
        }
    }

    fun mapHistoryDomainModelToFilterList(history: List<HistoryDtoModel>): List<String> {
        val filterList = mutableListOf<String>()
        history.map {
            filterList.add(it.currencyNameChild)
            filterList.add(it.currencyNameParent)
        }
        return filterList.distinct().sorted()
    }

    fun mapHistoryUiModelToDomainModel(historyUiModel: HistoryUiModel): HistoryDtoModel {
        return HistoryDtoModel(
            currencyNameParent = historyUiModel.currencyNameParent,
            currencyValueParent = historyUiModel.currencyValueParent,
            currencyNameChild = historyUiModel.currencyNameChild,
            currencyValueChild = historyUiModel.currencyValueChild,
            date = LocalDateTime.now()
        )
    }

    fun mapHistoryDomainModelToEntity(historyDomainModel: HistoryDtoModel): HistoryEntity {
        return HistoryEntity(
            id = 0,
            currencyNameParent = historyDomainModel.currencyNameParent,
            currencyValueParent = historyDomainModel.currencyValueParent,
            currencyNameChild = historyDomainModel.currencyNameChild,
            currencyValueChild = historyDomainModel.currencyValueChild,
            date = historyDomainModel.date
        )
    }
}