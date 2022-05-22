package com.example.itogovoe.ui.fragments.currency

import com.example.itogovoe.ui.model.CurrencyUiModel

interface CurrencyPassClick {
    fun passIsFavouriteClick(currencyName: String, isFavourite: Boolean)
    fun passClick(currencyChildName: String)
    fun passLongClick(currencyUiModel: CurrencyUiModel)
}