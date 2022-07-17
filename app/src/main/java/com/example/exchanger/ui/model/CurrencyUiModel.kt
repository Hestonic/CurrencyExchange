package com.example.exchanger.ui.model

import java.time.LocalDateTime

data class CurrenciesUiModel(
    val isError: Boolean,
    val isLoading: Boolean,
    val currencies: List<CurrencyUiModel>,
)

data class CurrencyUiModel(
    var isChecked: Boolean,
    var isFavourite: Boolean,
    val lastUsedAt: LocalDateTime,
    val name: String,
)