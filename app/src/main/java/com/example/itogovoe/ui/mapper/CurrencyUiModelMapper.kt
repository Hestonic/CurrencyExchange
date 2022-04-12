package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.ui.model.CurrencyUiModel

object CurrencyUiModelMapper {
    fun mapDomainModelToUiModel(currencies: Currencies): List<CurrencyUiModel>? {
         return currencies.rates?.map { CurrencyUiModel(
             isNotChecked = true,
             isFavourite = false,
             name = it.name,
             value = it.value
         ) }
    }
}