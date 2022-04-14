package com.example.itogovoe.ui.fragments.filter

data class FilterState(
    var allTimeColorBg: Boolean,
    var monthColorBg: Boolean,
    var weekColorBg: Boolean,
    var selectedCurrencies: MutableList<String>
)
