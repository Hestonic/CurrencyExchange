package com.example.exchanger.domain.model

import java.time.LocalDateTime

data class CurrencyDtoModel(
    val updatedAt: LocalDateTime,
    val lastUsedAt: LocalDateTime,
    val name: String,
    val value: Float,
    val isFavourite: Boolean
)
