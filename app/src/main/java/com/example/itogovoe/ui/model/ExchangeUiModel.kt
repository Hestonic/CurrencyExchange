package com.example.itogovoe.ui.model

data class ExchangeUiModel(
    val currencyParentName: String,
    val currencyChildName: String,
    val currencyChildValue: Float,
    val currencyParentValue: Float
)
