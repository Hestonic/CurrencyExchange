package com.example.exchanger.ui.model

data class FilterUiModel(
    val timeFilters: List<TimeFilterUiModel>,
    val timeRange: TimeRangeUiModel,
    val currencyChips: List<CurrencyChipsUiModel>
)

data class TimeFilterUiModel(
    val name: String,
    val isChecked: Boolean
)

data class TimeRangeUiModel(
    val dateFrom: String,
    val dateTo: String,
    val isChecked: Boolean
)

data class CurrencyChipsUiModel(
    val name: String,
    val isChecked: Boolean
)
