package com.example.exchanger.ui.mapper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object UiDateConverter {
    fun localDateTimeToLocalDateTimeString(date: LocalDateTime): String =
        date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)).toString()
    
    fun localDateTimeToLocalDateString(date: LocalDateTime): String =
        date.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString()
}