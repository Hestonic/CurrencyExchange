package com.example.itogovoe.data.api

data class CurrencyResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String?,
    val date: String,
    val rates: Map<String, Double>
)