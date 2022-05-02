package com.example.itogovoe.data.sources.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class CurrenciesEntity(
    @PrimaryKey val name: String,
    val value: Float,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastUsedAt: LocalDateTime,
    val isFavourite: Boolean,
)