package com.example.itogovoe.ui.main

import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime

object FilterInstance {
    val filters: MutableLiveData<FiltersModel> = MutableLiveData()
    
    fun initFilterLiveData() {
        filters.postValue(
            FiltersModel(
                timeFilter = TimeFilter.AllTime,
                currencyFilter = CurrencyFilter(emptyList())
            )
        )
    }
}

data class FiltersModel(
    val timeFilter: TimeFilter = TimeFilter.AllTime,
    val currencyFilter: CurrencyFilter = CurrencyFilter(emptyList())
)

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