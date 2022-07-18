package com.example.exchanger.domain.model

import java.time.LocalDateTime

data class HistoryDtoModel(
    val date: LocalDateTime,
    val currencyNameParent: String,
    val currencyValueParent: Float,
    val currencyNameChild: String,
    val currencyValueChild: Float
)