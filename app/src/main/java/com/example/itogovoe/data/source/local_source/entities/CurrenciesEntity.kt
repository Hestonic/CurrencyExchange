package com.example.itogovoe.data.source.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrenciesEntity(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val name: String,
    val value: Double
)