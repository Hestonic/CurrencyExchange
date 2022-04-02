package com.example.itogovoe.ui.model

import java.time.LocalDate

data class History(
    val date: LocalDate,
    val currencyNameParent: String,
    val currencyValueParent: Double,
    val currencyNameChild: String,
    val currencyValueChild: Double,
)