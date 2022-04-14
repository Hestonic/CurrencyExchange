package com.example.itogovoe.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.fragments.filter.FilterState
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import com.example.itogovoe.ui.model.HistoryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    // TODO: тут происходит фильтрация данных для UI в adapter, тут будет фильтроваться isFavourite со звёздочкой
    val itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val historyItems: MutableLiveData<List<HistoryUiModel>> = MutableLiveData()
    val filterItems: MutableLiveData<List<String>> = MutableLiveData()

    val filterState: LiveData<FilterState> get() = filterStateLiveData
    private val filterStateLiveData = MutableLiveData<FilterState>()

    fun initFilterState(filterState: FilterState) {
        filterStateLiveData.value = filterState
    }

    fun selectFilter(filter: String) {
        val oldFilterState = filterStateLiveData.value
        when (filter) {
            "За всё время" -> filterStateLiveData.value = oldFilterState?.copy(
                allTimeColorBg = true,
                monthColorBg = false,
                weekColorBg = false,
            )
            "За месяц" -> filterStateLiveData.value = oldFilterState?.copy(
                allTimeColorBg = false,
                monthColorBg = true,
                weekColorBg = false,
            )
            "За неделю" -> filterStateLiveData.value = oldFilterState?.copy(
                allTimeColorBg = false,
                monthColorBg = false,
                weekColorBg = true,
            )
        }
    }

    fun selectCurrency(selectedCurrency: String) {
        val oldFilterState = filterStateLiveData.value
        oldFilterState?.selectedCurrencies?.add(selectedCurrency)
        filterStateLiveData.value = oldFilterState?.copy(
            selectedCurrencies = oldFilterState.selectedCurrencies
        )
    }

    fun getFilterItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let {
                filterItems.postValue(CurrencyUiModelMapper.mapHistoryEntityToFilterList(it))
            }
        }
    }

    fun getHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let {
                historyItems.postValue(CurrencyUiModelMapper.mapHistoryEntityToUiModel(it))
            }
        }
    }

    fun getCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()?.let {
                itemsLiveData.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }
        }
    }

    fun addHistoryItem(historyEntity: HistoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistoryItem(historyEntity)
        }
    }

    fun isFresh(): LiveData<Boolean> {
        val isFresh = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            isFresh.postValue(repository.isFresh())
        }
        return isFresh
    }
}