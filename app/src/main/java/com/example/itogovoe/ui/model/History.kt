package com.example.itogovoe.ui.model

import java.time.LocalDateTime

data class History(
    val date: LocalDateTime,
    val currencyNameParent: String,
    val currencyValueParent: Double,
    val currencyNameChild: String,
    val currencyValueChild: Double,
)
