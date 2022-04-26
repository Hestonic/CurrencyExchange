package com.example.itogovoe.ui.fragments.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.main.FilterInstance
import com.example.itogovoe.ui.main.TimeFilter
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import com.example.itogovoe.ui.model.HistoryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CurrencyViewModel(private val repository: Repository) : ViewModel() {

    val itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val historyItems: MutableLiveData<List<HistoryUiModel>> = MutableLiveData()
    val historySearchItems: MutableLiveData<List<HistoryUiModel>> = MutableLiveData()
    val filterItems: MutableLiveData<List<String>> = MutableLiveData()

    fun getFilterItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let {
                filterItems.postValue(HistoryUiModelMapper.mapHistoryDomainModelToFilterList(it))
            }
        }
    }

    fun getHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let {
                historyItems.postValue(HistoryUiModelMapper.mapHistoryEntityToUiModel(it))
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

    fun getData() {
        return when(val timeFilter = FilterInstance.timeFilter){
            is TimeFilter.AllTime -> Unit
            is TimeFilter.Month -> getData(TimeFilter.Month.from, TimeFilter.Month.to)
            is TimeFilter.Range -> getData(timeFilter.from, timeFilter.to)
            is TimeFilter.Week -> getData(TimeFilter.Week.from, TimeFilter.Week.to)
        }
    }

    private fun getData(from: LocalDateTime, to: LocalDateTime){
        viewModelScope.launch(Dispatchers.IO) {
            // TODO:
            /*repository.searchDateHistory(null, FilterInstance.dateTo).let {
                historySearchItems.postValue(CurrencyUiModelMapper.mapHistoryEntityToUiModel(it))
            }*/
        }
    }

}