package com.example.itogovoe.ui.fragments.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.main.FilterInstance
import com.example.itogovoe.ui.main.TimeFilter
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import com.example.itogovoe.ui.model.HistoryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HistoryViewModel(private val repository: Repository) : ViewModel() {

    private val _historySearchItems: MutableLiveData<List<HistoryUiModel>> = MutableLiveData()
    val historySearchItems: LiveData<List<HistoryUiModel>> = _historySearchItems

    private val _historyItems: MutableLiveData<List<HistoryUiModel>> = MutableLiveData()
    val historyItems: LiveData<List<HistoryUiModel>> = _historyItems

    fun getHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let {
                _historyItems.postValue(HistoryUiModelMapper.mapHistoryEntityToUiModel(it))
            }
        }
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
            // TODO: Filter
            /*repository.searchDateHistory(null, FilterInstance.dateTo).let {
                historySearchItems.postValue(CurrencyUiModelMapper.mapHistoryEntityToUiModel(it))
            }*/
        }
    }

}