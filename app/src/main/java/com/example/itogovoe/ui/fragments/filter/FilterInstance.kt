package com.example.itogovoe.ui.fragments.filter

import java.time.LocalDateTime

object FilterInstance {
    var allTimeColorBg: Boolean = true
    var monthColorBg: Boolean = false
    var weekColorBg: Boolean = false
    var selectedCurrencies: MutableList<String> = mutableListOf()
    var dateTo: LocalDateTime? = null
    var dateFrom: LocalDateTime? = null
}