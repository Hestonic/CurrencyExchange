package com.example.itogovoe.domain.model

import java.time.LocalDateTime

data class HistoryDtoModel(
    val date: LocalDateTime,
    val currencyNameParent: String,
    val currencyValueParent: Double,
    val currencyNameChild: String,
    val currencyValueChild: Double
)