package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.CurrencyDtoModel
import com.example.itogovoe.ui.model.CurrencyUiModel

object CurrencyUiModelMapper {
    
    fun mapDomainModelToUiModel(currencies: List<CurrencyDtoModel>): List<CurrencyUiModel> =
        currencies.map {
            CurrencyUiModel(
                isChecked = false,
                isFavourite = it.isFavourite,
                lastUsedAt = it.lastUsedAt,
                name = it.name
            )
        }
    
    fun mapCurrencyUiModelIsChecked(currencyUiModel: CurrencyUiModel): CurrencyUiModel =
        CurrencyUiModel(
            isChecked = true,
            isFavourite = currencyUiModel.isFavourite,
            lastUsedAt = currencyUiModel.lastUsedAt,
            name = currencyUiModel.name
        )
}