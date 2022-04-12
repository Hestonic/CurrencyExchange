package com.example.itogovoe.ui.model

data class CurrencyUiModel(
    var isNotChecked: Boolean = true,
    var isFavourite: Boolean = false,
    val name: String,
    val value: Double
)


