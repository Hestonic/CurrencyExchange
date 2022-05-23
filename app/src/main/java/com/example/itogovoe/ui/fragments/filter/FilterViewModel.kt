package com.example.itogovoe.ui.fragments.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.HistoryRepository
import com.example.itogovoe.ui.main.FilterInstance
import com.example.itogovoe.ui.main.TimeFilter
import com.example.itogovoe.ui.mapper.FilterUiModelMapper
import com.example.itogovoe.ui.mapper.UiDateConverter
import com.example.itogovoe.ui.model.CurrencyChipsUiModel
import com.example.itogovoe.ui.model.FilterUiModel
import com.example.itogovoe.ui.model.TimeFilterUiModel
import com.example.itogovoe.ui.model.TimeRangeUiModel
import com.example.itogovoe.utils.Constants.Companion.FILTER_ALL_TIME
import com.example.itogovoe.utils.Constants.Companion.FILTER_MONTH
import com.example.itogovoe.utils.Constants.Companion.FILTER_WEEK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class FilterViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    
    private val _filtersLiveData: MutableLiveData<FilterUiModel> = MutableLiveData()
    val filterLiveData: LiveData<FilterUiModel> get() = _filtersLiveData
    
    private fun getCurrenciesFilter(): List<CurrencyChipsUiModel> {
        historyRepository.getHistory().let { historyDtoModelList ->
            return FilterUiModelMapper.mapHistoryDtoToCurrencyChipsUiModel(
                historyDtoModelList
            )
        }
    }
    
    fun initFilterUiModel(timeFilter: TimeFilter) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_filtersLiveData.value == null)
                _filtersLiveData.postValue(FilterUiModelMapper.mapFilterUiModel(getCurrenciesFilter()))
            
            when (timeFilter) {
                is TimeFilter.AllTime -> updateTimeFilterChips(FILTER_ALL_TIME)
                is TimeFilter.Month -> updateTimeFilterChips(FILTER_MONTH)
                is TimeFilter.Week -> updateTimeFilterChips(FILTER_WEEK)
                is TimeFilter.Range -> updateTimeFilterChooser(timeFilter.from, timeFilter.to)
            }
        }
    }
    
    fun updateTimeFilterChips(name: String) {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            val timeFilterUiModelList = mutableListOf<TimeFilterUiModel>()
            oldFilters?.timeFilters?.forEach { filter ->
                if (filter.name == name) timeFilterUiModelList.add(TimeFilterUiModel(name, true))
                else timeFilterUiModelList.add(TimeFilterUiModel(filter.name, false))
            }
            _filtersLiveData.postValue(
                oldFilters?.copy(
                    timeFilters = timeFilterUiModelList,
                    timeRange = oldFilters.timeRange.copy(isChecked = false),
                )
            )
        }
    }
    
    private fun updateTimeFilterChooser(dateFrom: LocalDateTime, dateTo: LocalDateTime) {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            val timeFilterUiModelList = mutableListOf<TimeFilterUiModel>()
            oldFilters?.timeFilters?.forEach { filter ->
                timeFilterUiModelList.add(TimeFilterUiModel(filter.name, false))
            }
            _filtersLiveData.postValue(
                oldFilters?.copy(
                    timeFilters = timeFilterUiModelList,
                    timeRange = TimeRangeUiModel(
                        dateFrom = UiDateConverter.localDateTimeToLocalDateString(dateFrom),
                        dateTo = UiDateConverter.localDateTimeToLocalDateString(dateTo),
                        isChecked = true
                    )
                )
            )
        }
    }
    
    fun setTimeFilterChipsInInstance(name: String) {
        when (name) {
            FILTER_ALL_TIME -> FilterInstance.timeFilter.postValue(TimeFilter.AllTime)
            FILTER_MONTH -> FilterInstance.timeFilter.postValue(TimeFilter.Month)
            FILTER_WEEK -> FilterInstance.timeFilter.postValue(TimeFilter.Week)
        }
    }
    
    fun dateFromChooser(year: Int, month: Int, day: Int) {
        val oldTimeFilterRange = FilterInstance.timeFilter.value
        if (oldTimeFilterRange is TimeFilter.Range)
            FilterInstance.timeFilter.postValue(
                TimeFilter.Range(
                    from = LocalDateTime.of(year, month + 1, day, 0, 0, 1),
                    to = oldTimeFilterRange.to,
                )
            )
        else FilterInstance.timeFilter.postValue(
            TimeFilter.Range(
                from = LocalDateTime.of(year, month + 1, day, 0, 0, 1),
                to = LocalDateTime.now(),
            )
        )
    }
    
    fun dateToChooser(year: Int, month: Int, day: Int) {
        val timeFilterRange = FilterInstance.timeFilter.value
        if (timeFilterRange is TimeFilter.Range)
            FilterInstance.timeFilter.postValue(
                TimeFilter.Range(
                    from = timeFilterRange.from,
                    to = LocalDateTime.of(year, month + 1, day, 23, 50, 59),
                )
            )
        else FilterInstance.timeFilter.postValue(
            TimeFilter.Range(
                from = LocalDateTime.now(),
                to = LocalDateTime.of(year, month + 1, day, 23, 50, 59),
            )
        )
    }
}