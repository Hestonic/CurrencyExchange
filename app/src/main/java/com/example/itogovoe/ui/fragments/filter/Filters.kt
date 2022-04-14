package com.example.itogovoe.ui.fragments.filter

import java.time.LocalDateTime

sealed class Filters {
    class DateFilter(val dateFrom: LocalDateTime, val dateTo: LocalDateTime): Filters()
    class CurrencyFilter(val currencies: List<String>): Filters()
}