package com.example.exchanger.ui.fragments.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchanger.domain.model.HistoryDtoModel
import com.example.exchanger.domain.repository.HistoryRepository
import com.example.exchanger.ui.main.CurrencyFilterModel
import com.example.exchanger.ui.main.FilterInstance
import com.example.exchanger.ui.main.TimeFilter
import com.example.exchanger.ui.mapper.HistoryUiModelMapper
import com.example.exchanger.ui.model.History
import com.example.exchanger.ui.model.HistoryChips
import com.example.exchanger.ui.model.HistoryUiModel
import com.example.exchanger.utils.Constants.Companion.FILTER_ALL_TIME
import com.example.exchanger.utils.Constants.Companion.FILTER_MONTH
import com.example.exchanger.utils.Constants.Companion.FILTER_WEEK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    
    companion object {
        const val CURRENCY_FILTER_NAME = "По валютам"
    }
    
    private val _allHistory: MutableLiveData<List<History>> = MutableLiveData()
    val allHistory: LiveData<List<History>> get() = _allHistory
    
    private val _historyChips: MutableLiveData<List<HistoryChips>> = MutableLiveData()
    val historyChips: LiveData<List<HistoryChips>> get() = _historyChips
    
    fun getData(timeFilter: TimeFilter) {
        return when (timeFilter) {
            is TimeFilter.AllTime -> getData()
            is TimeFilter.Month -> getData(TimeFilter.Month.from, TimeFilter.Month.to, FILTER_MONTH)
            is TimeFilter.Week -> getData(TimeFilter.Week.from, TimeFilter.Week.to, FILTER_WEEK)
            is TimeFilter.Range -> getData(timeFilter.from, timeFilter.to, "По выбранной дате")
        }
    }
    
    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.getHistory().let { historyDto ->
                FilterInstance.filters.value?.let { filtersModel ->
                    val allCurrenciesAsFilter =
                        filtersModel.currencyFilter.allCurrenciesAsFilter
                    val allHistoryUiModel = HistoryUiModelMapper.mapHistoryDtoToUiModel(historyDto)
                    if (currenciesAsFilterContainsSelectedItems(allCurrenciesAsFilter)) {
                        val filteredHistory = getFilteredHistoryByCurrencies(allCurrenciesAsFilter)
                        _allHistory.postValue(filteredHistory.history)
                        setTimeChipsUi(FILTER_ALL_TIME)
                    } else {
                        _allHistory.postValue(allHistoryUiModel.history)
                        setTimeChipsUi(allHistoryUiModel.historyChips, FILTER_ALL_TIME)
                    }
                }
            }
        }
    }
    
    private fun getData(from: LocalDateTime, to: LocalDateTime, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.searchDateHistory(from, to).let { historyDto ->
                FilterInstance.filters.value?.let { filtersModel ->
                    val allCurrenciesAsFilter =
                        filtersModel.currencyFilter.allCurrenciesAsFilter
                    val allHistoryUiModel = HistoryUiModelMapper.mapHistoryDtoToUiModel(historyDto)
                    if (currenciesAsFilterContainsSelectedItems(allCurrenciesAsFilter)) {
                        val filteredHistory =
                            getFilteredHistoryByCurrencies(allCurrenciesAsFilter, from, to)
                        _allHistory.postValue(filteredHistory.history)
                        setTimeChipsUi(name)
                    } else {
                        _allHistory.postValue(allHistoryUiModel.history)
                        setTimeChipsUi(allHistoryUiModel.historyChips, name)
                    }
    
                }
            }
        }
    }
    
    private fun getFilteredHistoryByCurrencies(allCurrenciesAsFilter: List<CurrencyFilterModel>): HistoryUiModel {
        val allHistoryFilteredByCurrencies: MutableList<HistoryDtoModel> =
            mutableListOf()
        allCurrenciesAsFilter.forEach { currencyFilterModel ->
            if (currencyFilterModel.isChecked)
                historyRepository.searchCurrenciesHistory(currencyFilterModel.name)
                    .forEach {
                        allHistoryFilteredByCurrencies.add(it)
                    }
        }
        return HistoryUiModelMapper.mapHistoryDtoToUiModel(allHistoryFilteredByCurrencies)
    }
    
    private fun getFilteredHistoryByCurrencies(
        allCurrenciesAsFilter: List<CurrencyFilterModel>,
        from: LocalDateTime,
        to: LocalDateTime
    ): HistoryUiModel {
        val allHistoryFilteredByCurrencies: MutableList<HistoryDtoModel> =
            mutableListOf()
        allCurrenciesAsFilter.forEach { currencyFilterModel ->
            if (currencyFilterModel.isChecked)
                historyRepository.searchCurrenciesHistory(currencyFilterModel.name)
                    .forEach {
                        if (to.isAfter(it.date) && from.isBefore(it.date))
                            allHistoryFilteredByCurrencies.add(it)
                    }
        }
        return HistoryUiModelMapper.mapHistoryDtoToUiModel(allHistoryFilteredByCurrencies)
    }
    
    private fun currenciesAsFilterContainsSelectedItems(
        allCurrenciesAsFilter: List<CurrencyFilterModel>?
    ): Boolean {
        allCurrenciesAsFilter?.let {
            if (it.isNotEmpty()) {
                it.forEach { currencyFilterModel ->
                    if (currencyFilterModel.isChecked) return true
                }
            }
        }
        return false
    }
    
    private fun setTimeChipsUi(historyChips: List<HistoryChips>, name: String) {
        val mutableHistoryChips = historyChips.toMutableList()
        mutableHistoryChips.removeAt(0)
        mutableHistoryChips.add(0, HistoryChips(true, name))
        _historyChips.postValue(mutableHistoryChips)
    }
    
    private fun setTimeChipsUi(timeName: String) {
        _historyChips.postValue(
            listOf(
                HistoryChips(true, timeName),
                HistoryChips(true, CURRENCY_FILTER_NAME)
            )
        )
    }
}