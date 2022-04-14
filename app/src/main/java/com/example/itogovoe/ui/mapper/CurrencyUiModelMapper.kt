package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.domain.model.HistoryDomainModel
import com.example.itogovoe.ui.model.CurrencyUiModel
import com.example.itogovoe.ui.model.HistoryUiModel

object CurrencyUiModelMapper {
    fun mapDomainModelToUiModel(currencies: Currencies): List<CurrencyUiModel>? {
        return currencies.rates?.map {
            CurrencyUiModel(
                isNotChecked = true,
                isFavourite = false,
                name = it.name,
                value = it.value
            )
        }
    }

    fun mapHistoryEntityToUiModel(history: List<HistoryDomainModel>): List<HistoryUiModel> {
        return history.map {
            HistoryUiModel(
                date = it.date.toString().replace("T", " "),
                currencyNameParent = it.currencyNameParent,
                currencyValueParent = it.currencyValueParent,
                currencyNameChild = it.currencyNameChild,
                currencyValueChild = it.currencyValueChild,
            )
        }
    }

    fun mapHistoryEntityToFilterList(history: List<HistoryDomainModel>): List<String> {
        val filterList = mutableListOf<String>()
        history.map {
            filterList.add(it.currencyNameChild)
            filterList.add(it.currencyNameParent)
        }
        return filterList.distinct().sorted()
    }
}