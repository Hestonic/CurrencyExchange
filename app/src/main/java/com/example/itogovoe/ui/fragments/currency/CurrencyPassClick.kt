package com.example.itogovoe.ui.fragments.currency

import com.example.itogovoe.ui.model.CurrencyUiModel

interface CurrencyPassClick {
    fun passIsFavouriteClick(currencyName: String, isFavourite: Boolean)
    fun passClick(
        currencyParentName: String,
        currencyParentValue: Double,
        currencyChildName: String,
        currencyChildValue: Double
    )
    fun passLongClick(currencyName: String)
    fun passIsCheckedLongClick(currencyUiModel: CurrencyUiModel)
}