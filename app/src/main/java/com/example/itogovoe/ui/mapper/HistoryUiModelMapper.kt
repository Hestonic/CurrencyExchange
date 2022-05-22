package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.model.History
import com.example.itogovoe.ui.model.HistoryChips
import com.example.itogovoe.ui.model.HistoryUiModel
import java.time.LocalDateTime
import com.example.itogovoe.utils.Constants.Companion.FILTER_ALL_TIME


object HistoryUiModelMapper {
    
    fun mapHistoryEntityToUiModel(historyDtoModelList: List<HistoryDtoModel>): HistoryUiModel {
        val history = historyDtoModelList.map { historyDtoModel ->
            History(
                date = historyDtoModel.date.toString().replace("T", " "),
                currencyNameParent = historyDtoModel.currencyNameParent,
                currencyValueParent = historyDtoModel.currencyValueParent,
                currencyNameChild = historyDtoModel.currencyNameChild,
                currencyValueChild = historyDtoModel.currencyValueChild,
            )
        }
        val historyChips = listOf(
            HistoryChips(true, FILTER_ALL_TIME),
            HistoryChips(false, "По валютам"),
        )
        return HistoryUiModel(history = history, historyChips = historyChips)
    }
    
    fun mapHistoryUiModelToDomainModel(history: History): HistoryDtoModel {
        return HistoryDtoModel(
            currencyNameParent = history.currencyNameParent,
            currencyValueParent = history.currencyValueParent,
            currencyNameChild = history.currencyNameChild,
            currencyValueChild = history.currencyValueChild,
            date = LocalDateTime.parse(history.date)
        )
    }
}