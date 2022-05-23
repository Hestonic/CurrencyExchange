package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.model.History
import com.example.itogovoe.ui.model.HistoryChips
import com.example.itogovoe.ui.model.HistoryUiModel
import com.example.itogovoe.utils.Constants.Companion.FILTER_ALL_TIME
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime


object HistoryUiModelMapper {
    
    fun mapHistoryEntityToUiModel(historyDtoModelList: List<HistoryDtoModel>): HistoryUiModel {
        val history = historyDtoModelList.map { historyDtoModel ->
            History(
                date = UiDateConverter.localDateTimeToLocalDateTimeString(historyDtoModel.date),
                currencyNameParent = historyDtoModel.currencyNameParent,
                currencyValueParent = roundFloat(historyDtoModel.currencyValueParent),
                currencyNameChild = historyDtoModel.currencyNameChild,
                currencyValueChild = roundFloat(historyDtoModel.currencyValueChild),
            )
        }
        val historyChips = listOf(
            HistoryChips(true, FILTER_ALL_TIME),
            HistoryChips(false, "По валютам"),
        )
        return HistoryUiModel(history = history, historyChips = historyChips)
    }
    
    fun mapHistoryModelToDtoModel(history: History): HistoryDtoModel =
        HistoryDtoModel(
            date = LocalDateTime.parse(history.date),
            currencyNameParent = history.currencyNameParent,
            currencyValueParent = history.currencyValueParent,
            currencyNameChild = history.currencyNameChild,
            currencyValueChild = history.currencyValueChild
        )
    
    fun mapHistoryModel(
        nameParent: String, nameChild: String,
        valueParent: Float, valueChild: Float
    ): History {
        return History(
            date = LocalDateTime.now().toString(),
            currencyNameParent = nameParent,
            currencyValueParent = valueParent,
            currencyNameChild = nameChild,
            currencyValueChild = valueChild,
        )
    }
    
    private fun roundFloat(value: Float): Float {
        val decimalFormat = DecimalFormat("#.####")
        decimalFormat.roundingMode = RoundingMode.FLOOR
        return decimalFormat.format(value).replace(",", ".").toFloat()
    }
}