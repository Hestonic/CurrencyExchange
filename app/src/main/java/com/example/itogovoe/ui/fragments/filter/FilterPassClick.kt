package com.example.itogovoe.ui.fragments.filter

import com.example.itogovoe.ui.model.CurrencyChipsUiModel

interface FilterPassClick {
    fun passChipsClick(name: String)
    fun passCurrencyChipsClick(clickedElement: CurrencyChipsUiModel)
}