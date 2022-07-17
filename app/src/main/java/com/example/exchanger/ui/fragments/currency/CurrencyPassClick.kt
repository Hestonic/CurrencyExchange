package com.example.exchanger.ui.fragments.currency

import com.example.exchanger.ui.model.CurrencyUiModel

interface CurrencyPassClick {
    fun passIsFavouriteClick(currencyName: String, isFavourite: Boolean)
    fun passClick(currencyChildName: String)
    fun passLongClick(currencyUiModel: CurrencyUiModel)
}