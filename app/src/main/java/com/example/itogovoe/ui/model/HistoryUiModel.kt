package com.example.itogovoe.ui.model

data class HistoryUiModel(
    val date: String,
    val currencyNameParent: String,
    val currencyValueParent: Double,
    val currencyNameChild: String,
    val currencyValueChild: Double
)
