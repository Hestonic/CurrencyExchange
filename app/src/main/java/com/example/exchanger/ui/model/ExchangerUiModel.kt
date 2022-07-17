package com.example.exchanger.ui.model

data class ExchangerUiModel(
    val isLoading: Boolean,
    val currencyNameParent: String,
    val currencyValueParent: Float,
    val currencyNameChild: String,
    val currencyValueChild: Float,
)