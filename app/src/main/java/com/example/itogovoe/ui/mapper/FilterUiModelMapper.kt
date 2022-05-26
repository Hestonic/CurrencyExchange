package com.example.itogovoe.ui.mapper

import com.example.itogovoe.domain.model.HistoryDtoModel
import com.example.itogovoe.ui.main.CurrencyFilter
import com.example.itogovoe.ui.main.CurrencyFilterModel
import com.example.itogovoe.ui.main.FiltersModel
import com.example.itogovoe.ui.main.TimeFilter
import com.example.itogovoe.ui.model.CurrencyChipsUiModel
import com.example.itogovoe.ui.model.FilterUiModel
import com.example.itogovoe.ui.model.TimeFilterUiModel
import com.example.itogovoe.ui.model.TimeRangeUiModel
import com.example.itogovoe.utils.Constants.Companion.FILTER_ALL_TIME
import com.example.itogovoe.utils.Constants.Companion.FILTER_MONTH
import com.example.itogovoe.utils.Constants.Companion.FILTER_WEEK
import java.time.LocalDateTime

object FilterUiModelMapper {

    fun mapHistoryDtoToCurrencyChipsUiModel(history: List<HistoryDtoModel>): List<CurrencyChipsUiModel> {
        val filterList = mutableListOf<String>()
        history.forEach {
            filterList.add(it.currencyNameChild)
            filterList.add(it.currencyNameParent)
        }
        return filterList.distinct().sorted().map {
            CurrencyChipsUiModel(
                name = it,
                isChecked = false
            )
        }
    }
    
    fun mapFilterUiModel(currencyChips: List<CurrencyChipsUiModel>): FilterUiModel =
        FilterUiModel(
            timeFilters = listOf(
                TimeFilterUiModel(FILTER_ALL_TIME, true),
                TimeFilterUiModel(FILTER_MONTH, false),
                TimeFilterUiModel(FILTER_WEEK, false),
            ),
            timeRange = TimeRangeUiModel(
                "Выбрать дату",
                UiDateConverter.localDateTimeToLocalDateString(LocalDateTime.now()),
                false
            ),
            currencyChips = currencyChips,
        )
    
    fun mapCurrenciesAsFilterToCurrencyChips(
        allCurrenciesAsFilter: List<CurrencyFilterModel>
    ): List<CurrencyChipsUiModel> =
        allCurrenciesAsFilter.map { CurrencyChipsUiModel(isChecked = it.isChecked, name = it.name) }
    
    fun handleCurrencyChipClick(
        oldFilter: FilterUiModel,
        clickedElement: CurrencyChipsUiModel
    ): CurrencyFilter {
        val currencyChips = oldFilter.currencyChips.toMutableList()
        val indexOfCheckedElement = currencyChips.indexOf(clickedElement)
        if (clickedElement.isChecked)
            currencyChips[indexOfCheckedElement] = clickedElement.copy(isChecked = false)
        else currencyChips[indexOfCheckedElement] = clickedElement.copy(isChecked = true)
        return mapCurrencyChipsUiModelToCurrencyFilter(currencyChips)
    }
    
    private fun mapCurrencyChipsUiModelToCurrencyFilter(currenciesChips: List<CurrencyChipsUiModel>): CurrencyFilter {
        val currencyFilterModel =
            currenciesChips.map { CurrencyFilterModel(isChecked = it.isChecked, name = it.name) }
        return CurrencyFilter(allCurrenciesAsFilter = currencyFilterModel)
    }
    
    fun refreshTimeFiltersModelByRange(
        oldFiltersModel: FiltersModel,
        dateFrom: LocalDateTime,
        dateTo: LocalDateTime
    ): FiltersModel =
        oldFiltersModel.copy(timeFilter = TimeFilter.Range(from = dateFrom, to = dateTo))
}