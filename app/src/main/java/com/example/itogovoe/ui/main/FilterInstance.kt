package com.example.itogovoe.ui.main

import java.time.LocalDateTime

object FilterInstance {
    var timeFilter: TimeFilter = TimeFilter.AllTime
    // var currencyFilter: CurrencyFilter = CurrencyFilter()
}

data class CurrencyFilter(
    val allCurrenciesAsFilter: List<CurrencyFilterModel>,
)

data class CurrencyFilterModel(
        val name: String,
        val isChecked: Boolean
)

sealed class TimeFilter {

    object AllTime : TimeFilter() {
        val to: LocalDateTime
            get() = LocalDateTime.now()
    }

    object Month : TimeFilter() {
        val from: LocalDateTime
            get() = LocalDateTime.now().minusMonths(1)
        val to: LocalDateTime
            get() = LocalDateTime.now()
    }

    object Week : TimeFilter() {
        val from: LocalDateTime
            get() = LocalDateTime.now().minusDays(7)
        val to: LocalDateTime
            get() = LocalDateTime.now()
    }

    data class Range(val from: LocalDateTime, val to: LocalDateTime) : TimeFilter()

}