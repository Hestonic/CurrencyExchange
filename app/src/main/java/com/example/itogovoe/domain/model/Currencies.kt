package com.example.itogovoe.domain.model

import java.util.*

data class Currencies(
    val data: Date,
    val base: String,
    val rates: List<Currency>
)


data class Currency(
    val name: String,
    val value: Double
)