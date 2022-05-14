package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.model.CurrencyChipsUiModel
import com.example.itogovoe.ui.model.FilterUiModel
import com.example.itogovoe.ui.model.TimeFilterUiModel
import com.example.itogovoe.ui.model.TimeRangeUiModel
import java.time.LocalDateTime

object FilterUiModelMapper {

    fun mapHistoryDomainModelToCurrencyChipsUiModel(history: List<HistoryDtoModel>): List<CurrencyChipsUiModel> {
        val filterList = mutableListOf<String>()
        history.map {
            filterList.add(it.currencyNameChild)
            filterList.add(it.currencyNameParent)
        }.distinct().sorted()
        return filterList.map {
            CurrencyChipsUiModel(
                name = it,
                isChecked = false
            )
        }
    }

    fun mapFilterUiModel(currencyChips: List<CurrencyChipsUiModel>): FilterUiModel {
        return FilterUiModel(
            timeFilters = listOf(
                TimeFilterUiModel("За всё время", true),
                TimeFilterUiModel("За месяц", false),
                TimeFilterUiModel("За неделю", false),
            ),
            timeRange = TimeRangeUiModel(
                "Выбрать дату",
                LocalDateTime.now().toString(),
                false
            ),
            currencyChips = currencyChips,
        )
    }

}