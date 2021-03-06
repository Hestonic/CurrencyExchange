package com.example.exchanger.ui.mapper

import com.example.exchanger.domain.model.CurrencyDtoModel
import com.example.exchanger.ui.model.CurrenciesUiModel
import com.example.exchanger.ui.model.CurrencyUiModel

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
    
    private fun mapCurrencies(currenciesDtoModel: List<CurrencyDtoModel>): List<CurrencyUiModel> =
        currenciesDtoModel.map {
            CurrencyUiModel(
                isChecked = it.isChecked,
                isFavourite = it.isFavourite,
                lastUsedAt = it.lastUsedAt,
                name = it.name
            )
        }
}