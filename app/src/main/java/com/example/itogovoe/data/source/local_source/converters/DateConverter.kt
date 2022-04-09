package com.example.itogovoe.data.source.local_source.converters

import androidx.room.TypeConverter
import java.sql.Time
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DateConverter {
    @TypeConverter
    fun localDateTimeToTimestamp(date: LocalDateTime): Long =
        Timestamp.valueOf(date.toString()).toString().toLong()

    @TypeConverter
    fun timestampToLocalDateTime(timestamp: Long): LocalDateTime =
        Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
}