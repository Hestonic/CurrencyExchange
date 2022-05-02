package com.example.itogovoe.ui.fragments.history

import android.util.Log
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

    private val _historyItems: MutableLiveData<List<HistoryUiModel>> = MutableLiveData()
    val historyItems: LiveData<List<HistoryUiModel>> get() = _historyItems

    fun getData() {
        // TODO: не забыть удалить
        val timeFilter = FilterInstance.timeFilter
        Log.d("history_view_tag", "$timeFilter")
        return when (val timeFilter = FilterInstance.timeFilter) {
            is TimeFilter.AllTime -> getAllHistory()
            is TimeFilter.Month -> getData(TimeFilter.Month.from, TimeFilter.Month.to)
            is TimeFilter.Week -> getData(TimeFilter.Week.from, TimeFilter.Week.to)
            is TimeFilter.Range -> getData(timeFilter.from, timeFilter.to)
        }
    }

    private fun getAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let {
                _historyItems.postValue(HistoryUiModelMapper.mapHistoryEntityToUiModel(it))
            }
        }
    }

    private fun getData(from: LocalDateTime, to: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchDateHistory(from, to).let {
                _historyItems.postValue(HistoryUiModelMapper.mapHistoryEntityToUiModel(it))
            }
        }
    }

}