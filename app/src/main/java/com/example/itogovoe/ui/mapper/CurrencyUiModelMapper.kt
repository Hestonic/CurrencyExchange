package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.CurrencyDtoModel
import com.example.itogovoe.ui.model.CurrenciesUiModel
import com.example.itogovoe.ui.model.CurrencyUiModel

object CurrencyUiModelMapper {
    
    fun mapCurrencyUiModel(): CurrenciesUiModel =
        CurrenciesUiModel(
            isError = false,
            isLoading = true,
            currencies = emptyList(),
        )
    
    fun mapCurrencyError(): CurrenciesUiModel =
        CurrenciesUiModel(
            isError = true,
            isLoading = false,
            currencies = emptyList(),
        )
    
    fun mapDomainModelToUiModel(currenciesDtoModel: List<CurrencyDtoModel>): CurrenciesUiModel =
        CurrenciesUiModel(
            isError = false,
            isLoading = false,
            currencies = mapCurrencies(currenciesDtoModel),
        )
    
    fun mapCurrencyUiModelIsChecked(currencyUiModel: CurrencyUiModel): CurrencyUiModel =
        CurrencyUiModel(
            isChecked = true,
            isFavourite = currencyUiModel.isFavourite,
            lastUsedAt = currencyUiModel.lastUsedAt,
            name = currencyUiModel.name
        )
    
    private fun mapCurrencies(currenciesDtoModel: List<CurrencyDtoModel>): List<CurrencyUiModel> =
        currenciesDtoModel.map {
            CurrencyUiModel(
                isChecked = false,
                isFavourite = it.isFavourite,
                lastUsedAt = it.lastUsedAt,
                name = it.name
            )
        }
}