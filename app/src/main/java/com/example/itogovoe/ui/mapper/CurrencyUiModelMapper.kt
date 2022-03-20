package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.domain.model.Currency
import com.example.itogovoe.ui.model.CurrencyUiModel

object CurrencyUiModelMapper {
    fun mapDomainModelToUiModel(currencies: Currencies): List<CurrencyUiModel> {

        val rates: MutableList<CurrencyUiModel> = mutableListOf()
        for ((name, value) in currencies.rates) {
            val currencyUiModel = CurrencyUiModel(name, value)
            rates.add(currencyUiModel)
        }

        return rates
    }
}