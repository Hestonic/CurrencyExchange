package com.example.itogovoe.ui.model

data class ExchangerUiModel(
    val isLoading: Boolean,
    val currencyNameParent: String,
    val currencyValueParent: Float,
    val currencyNameChild: String,
    val currencyValueChild: Float,
)