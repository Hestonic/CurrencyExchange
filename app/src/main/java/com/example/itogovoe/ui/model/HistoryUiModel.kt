package com.example.itogovoe.ui.model

import java.time.LocalDateTime

data class HistoryUiModel(
    val date: String,
    val currencyNameParent: String,
    val currencyValueParent: Double,
    val currencyNameChild: String,
    val currencyValueChild: Double
)
