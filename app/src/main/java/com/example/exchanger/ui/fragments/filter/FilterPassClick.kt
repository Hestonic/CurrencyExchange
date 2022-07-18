package com.example.exchanger.ui.fragments.filter

import com.example.exchanger.ui.model.CurrencyChipsUiModel

interface FilterPassClick {
    fun passChipsClick(name: String)
    fun passCurrencyChipsClick(clickedElement: CurrencyChipsUiModel)
}