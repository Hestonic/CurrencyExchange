package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.model.CurrencyChipsUiModel
import com.example.itogovoe.ui.model.FilterUiModel
import com.example.itogovoe.ui.model.TimeFilterUiModel
import com.example.itogovoe.ui.model.TimeRangeUiModel
import java.time.LocalDateTime
import com.example.itogovoe.utils.Constants.Companion.FILTER_ALL_TIME
import com.example.itogovoe.utils.Constants.Companion.FILTER_MONTH
import com.example.itogovoe.utils.Constants.Companion.FILTER_WEEK
import com.example.itogovoe.utils.Constants.Companion.CHOOSE_DATE

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
                TimeFilterUiModel(FILTER_ALL_TIME, true),
                TimeFilterUiModel(FILTER_MONTH, false),
                TimeFilterUiModel(FILTER_WEEK, false),
            ),
            timeRange = TimeRangeUiModel(
                CHOOSE_DATE,
                LocalDateTime.now().toString(),
                false
            ),
            currencyChips = currencyChips,
        )
    }

}