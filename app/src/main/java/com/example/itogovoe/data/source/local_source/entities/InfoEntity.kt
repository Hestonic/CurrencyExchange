package com.example.itogovoe.data.source.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class InfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val lastUploadDate: LocalDateTime,
    val base: String
)
