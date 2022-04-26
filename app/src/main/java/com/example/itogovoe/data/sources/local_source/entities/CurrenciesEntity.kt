package com.example.itogovoe.data.sources.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrenciesEntity(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val name: String,
    val value: Double,
    /*val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastUsedAt: LocalDateTime,
    val isFavourite: Boolean*/
)