package com.example.itogovoe.ui.mapper

import com.example.itogovoe.data.sources.local_source.DateConverter
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.model.CurrencyChipsUiModel
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

    fun mapHistoryUiModelToDomainModel(historyUiModel: HistoryUiModel): HistoryDtoModel {
        return HistoryDtoModel(
            currencyNameParent = historyUiModel.currencyNameParent,
            currencyValueParent = historyUiModel.currencyValueParent,
            currencyNameChild = historyUiModel.currencyNameChild,
            currencyValueChild = historyUiModel.currencyValueChild,
            date = LocalDateTime.parse(historyUiModel.date)
        )
    }

}