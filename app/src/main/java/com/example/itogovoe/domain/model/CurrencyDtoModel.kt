package com.example.itogovoe.domain.model

import java.time.LocalDateTime

data class CurrencyDtoModel(
    val updatedAt: LocalDateTime?,
    val lastUsedAt: LocalDateTime?,
    val name: String?,
    val value: Double?,
    val isFavourite: Boolean?
)
