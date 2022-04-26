package com.example.itogovoe.ui.fragments.filter

import com.example.itogovoe.ui.main.TimeFilter
import java.sql.Time

data class FilterUiModel(
    val timeFilters: List<TimeFilterUiModel>,
    val timeRange: TimeRangeUiModel,
    val currencyChips: List<CurrencyChipsUiModel>
)

data class TimeFilterUiModel(
    val text: String,
    val isChecked: Boolean
)

data class TimeRangeUiModel(
    val dateFrom: String,
    val dateTo: String
)

data class CurrencyChipsUiModel(
    val name: String,
    val isChecked: Boolean
)

