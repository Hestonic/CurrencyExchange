package com.example.itogovoe.ui.fragments.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.HistoryRepository
import com.example.itogovoe.ui.main.TimeFilter
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import com.example.itogovoe.ui.model.History
import com.example.itogovoe.ui.model.HistoryChips
import com.example.itogovoe.utils.Constants.Companion.FILTER_ALL_TIME
import com.example.itogovoe.utils.Constants.Companion.FILTER_MONTH
import com.example.itogovoe.utils.Constants.Companion.FILTER_WEEK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    
    private val _allHistory: MutableLiveData<List<History>> = MutableLiveData()
    val allHistory: LiveData<List<History>> get() = _allHistory
    
    private val _historyChips: MutableLiveData<List<HistoryChips>> = MutableLiveData()
    val historyChips: LiveData<List<HistoryChips>> get() = _historyChips
    
    fun getData(timeFilter: TimeFilter) {
        return when (timeFilter) {
            is TimeFilter.AllTime -> getAllHistory()
            is TimeFilter.Month -> getData(TimeFilter.Month.from, TimeFilter.Month.to, FILTER_MONTH)
            is TimeFilter.Week -> getData(TimeFilter.Week.from, TimeFilter.Week.to, FILTER_WEEK)
            is TimeFilter.Range -> getData(timeFilter.from, timeFilter.to, "По выбранной дате")
        }
    }
    
    private fun getAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.getHistory().let {
                val historyUiModel = HistoryUiModelMapper.mapHistoryEntityToUiModel(it)
                _allHistory.postValue(historyUiModel.history)
                setTimeChipsUi(historyUiModel.historyChips, FILTER_ALL_TIME)
            }
        }
    }
    
    private fun getData(from: LocalDateTime, to: LocalDateTime, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.searchDateHistory(from, to).let {
                val historyUiModel = HistoryUiModelMapper.mapHistoryEntityToUiModel(it)
                _allHistory.postValue(historyUiModel.history)
                setTimeChipsUi(historyUiModel.historyChips, name)
            }
        }
    }
    
    private fun setTimeChipsUi(historyChips: List<HistoryChips>, name: String) {
        val mutableHistoryChips = historyChips.toMutableList()
        mutableHistoryChips.removeAt(0)
        mutableHistoryChips.add(0, HistoryChips(true, name))
        _historyChips.postValue(mutableHistoryChips)
    }
}