package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.domain.model.Currency
import com.example.itogovoe.ui.model.CurrencyUiModel

object CurrencyUiModelMapper {
    fun mapDomainModelToUiModel(currencies: Currencies): List<CurrencyUiModel> {
         return currencies.rates.map { CurrencyUiModel(it.name, it.value) }
    }
}