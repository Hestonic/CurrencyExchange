package com.example.itogovoe.data.sources.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: LocalDateTime,
    val currencyNameParent: String,
    val currencyValueParent: Float,
    val currencyNameChild: String,
    val currencyValueChild: Float
)