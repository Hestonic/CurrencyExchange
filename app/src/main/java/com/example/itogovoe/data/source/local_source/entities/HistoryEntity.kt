package com.example.itogovoe.data.source.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.itogovoe.data.source.local_source.converters.DateConverter
import java.time.LocalDateTime

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: LocalDateTime,
    val currencyNameParent: String,
    val currencyValueParent: Double,
    val currencyNameChild: String,
    val currencyValueChild: Double
)