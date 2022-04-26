package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.HistoryDomainModel
import com.example.itogovoe.ui.model.HistoryUiModel

object HistoryUiModelMapper {

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

    fun mapHistoryDomainModelToFilterList(history: List<HistoryDomainModel>): List<String> {
        val filterList = mutableListOf<String>()
        history.map {
            filterList.add(it.currencyNameChild)
            filterList.add(it.currencyNameParent)
        }
        return filterList.distinct().sorted()
    }
}