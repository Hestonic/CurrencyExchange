package com.example.itogovoe.domain.model

import java.time.LocalDateTime

data class Currencies(
    val date: LocalDateTime,
    val base: String,
    val rates: List<Currency>
)

data class Currency(
    val name: String,
    val value: Double
)