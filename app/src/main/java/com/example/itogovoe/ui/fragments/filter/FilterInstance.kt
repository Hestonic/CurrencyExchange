package com.example.itogovoe.ui.fragments.filter

import java.time.LocalDateTime

object FilterInstance {
    var allTimeFilter: Boolean = true
    var monthFilter: Boolean = false
    var weekFilter: Boolean = false
    var rangeFilter: Boolean = false
    var selectedCurrencies: MutableList<String> = mutableListOf()
    var dateFrom: LocalDateTime? = null
    var dateTo: LocalDateTime = LocalDateTime.now()
}