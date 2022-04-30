package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.model.CurrencyChipsUiModel

object FilterUiModelMapper {

    fun mapHistoryDomainModelToCurrencyChipsUiModel(history: List<HistoryDtoModel>): List<CurrencyChipsUiModel> {
        val filterList = mutableListOf<String>()
        history.map {
            filterList.add(it.currencyNameChild)
            filterList.add(it.currencyNameParent)
        }.distinct().sorted()
        return filterList.map {
            CurrencyChipsUiModel(
                name = it,
                isChecked = false
            )
        }
    }

}