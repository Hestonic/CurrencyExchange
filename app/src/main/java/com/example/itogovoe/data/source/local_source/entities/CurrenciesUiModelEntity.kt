package com.example.itogovoe.data.source.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrenciesUiModelEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val position: Int
)
