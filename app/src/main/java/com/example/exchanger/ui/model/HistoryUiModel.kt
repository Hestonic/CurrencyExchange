package com.example.exchanger.ui.model

data class HistoryUiModel(
    val historyChips: List<HistoryChips>,
    val history: List<History>,
)

data class HistoryChips(
    val isVisible: Boolean,
    val name: String,
)

data class History(
    val date: String,
    val currencyNameParent: String,
    val currencyValueParent: Float,
    val currencyNameChild: String,
    val currencyValueChild: Float,
)

