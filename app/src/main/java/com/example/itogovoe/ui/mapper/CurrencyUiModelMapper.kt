package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.ui.model.CurrenciesUiModel

object CurrencyUiModelMapper {

    fun mapDomainModelToUiModel(currencies: Currencies): CurrenciesUiModel {
        return CurrenciesUiModel(currencies.rates)
    }
}