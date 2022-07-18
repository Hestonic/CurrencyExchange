package com.example.exchanger.ui.fragments.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchanger.domain.repository.HistoryRepository
import com.example.exchanger.ui.main.FilterInstance
import com.example.exchanger.ui.main.FiltersModel
import com.example.exchanger.ui.main.TimeFilter
import com.example.exchanger.ui.mapper.FilterUiModelMapper
import com.example.exchanger.ui.mapper.UiDateConverter
import com.example.exchanger.ui.model.CurrencyChipsUiModel
import com.example.exchanger.ui.model.FilterUiModel
import com.example.exchanger.ui.model.TimeFilterUiModel
import com.example.exchanger.ui.model.TimeRangeUiModel
import com.example.exchanger.utils.Constants.Companion.FILTER_ALL_TIME
import com.example.exchanger.utils.Constants.Companion.FILTER_MONTH
import com.example.exchanger.utils.Constants.Companion.FILTER_WEEK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

// TODO: Сделать мапперы где надо
class FilterViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    
    private val _filtersLiveData: MutableLiveData<FilterUiModel> = MutableLiveData()
    val filterLiveData: LiveData<FilterUiModel> get() = _filtersLiveData
    
    private fun getCurrenciesFilterDb(): List<CurrencyChipsUiModel> {
        historyRepository.getHistory().let { historyDtoModelList ->
            return FilterUiModelMapper.mapHistoryDtoToCurrencyChipsUiModel(
                historyDtoModelList
            )
        }
    }
    
    fun initFilterUiModel(filtersModel: FiltersModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val allCurrenciesAsFilter =
                filtersModel.currencyFilter.allCurrenciesAsFilter
            if (_filtersLiveData.value == null) {
                if (allCurrenciesAsFilter.isEmpty()) {
                    val filterUiModel =
                        FilterUiModelMapper.mapFilterUiModel(getCurrenciesFilterDb())
                    _filtersLiveData.postValue(filterUiModel)
                } else {
                    val currencyChips =
                        FilterUiModelMapper.mapCurrenciesAsFilterToCurrencyChips(
                            allCurrenciesAsFilter
                        )
                    val filterUiModel = FilterUiModelMapper.mapFilterUiModel(currencyChips)
                    _filtersLiveData.postValue(filterUiModel)
                }
            }
            chooseTimeFilter(filtersModel.timeFilter)
        }
    }
    
    private fun chooseTimeFilter(timeFilter: TimeFilter) {
        when (timeFilter) {
            is TimeFilter.AllTime -> updateTimeFilterChips(FILTER_ALL_TIME)
            is TimeFilter.Month -> updateTimeFilterChips(FILTER_MONTH)
            is TimeFilter.Week -> updateTimeFilterChips(FILTER_WEEK)
            is TimeFilter.Range -> updateTimeFilterChooser(timeFilter.from, timeFilter.to)
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
        val oldFiltersModel = FilterInstance.filters.value
        when (name) {
            FILTER_ALL_TIME ->
                FilterInstance.filters.postValue(oldFiltersModel?.copy(timeFilter = TimeFilter.AllTime))
            FILTER_MONTH ->
                FilterInstance.filters.postValue(oldFiltersModel?.copy(timeFilter = TimeFilter.Month))
            FILTER_WEEK ->
                FilterInstance.filters.postValue(oldFiltersModel?.copy(timeFilter = TimeFilter.Week))
        }
    }
    
    fun dateFromChooser(year: Int, month: Int, day: Int) {
        FilterInstance.filters.value?.let { oldFiltersModel ->
            val oldTimeFilter = oldFiltersModel.timeFilter
            if (oldTimeFilter is TimeFilter.Range) {
                FilterInstance.filters.postValue(
                    FilterUiModelMapper.refreshTimeFiltersModelByRange(
                        oldFiltersModel,
                        LocalDateTime.of(year, month + 1, day, 0, 0, 1),
                        oldTimeFilter.to
                    )
                )
            } else {
                FilterInstance.filters.postValue(
                    FilterUiModelMapper.refreshTimeFiltersModelByRange(
                        oldFiltersModel,
                        LocalDateTime.of(year, month + 1, day, 0, 0, 1),
                        LocalDateTime.now()
                    )
                )
            }
        }
    
    }
    
    fun dateToChooser(year: Int, month: Int, day: Int) {
        FilterInstance.filters.value?.let { oldFiltersModel ->
            val oldTimeFilter = oldFiltersModel.timeFilter
            if (oldTimeFilter is TimeFilter.Range) {
                FilterInstance.filters.postValue(
                    FilterUiModelMapper.refreshTimeFiltersModelByRange(
                        oldFiltersModel,
                        oldTimeFilter.from,
                        LocalDateTime.of(year, month + 1, day, 23, 50, 59)
                    )
                )
            } else {
                FilterInstance.filters.postValue(
                    FilterUiModelMapper.refreshTimeFiltersModelByRange(
                        oldFiltersModel,
                        LocalDateTime.now(),
                        LocalDateTime.of(year, month + 1, day, 23, 50, 59)
                    )
                )
            }
        }
        
    }
    
    fun updateCurrencyChips(clickedElement: CurrencyChipsUiModel) {
        _filtersLiveData.value?.let { oldFilter ->
            val freshCurrencyFilter =
                FilterUiModelMapper.handleCurrencyChipClick(oldFilter, clickedElement)
            val freshCurrencyChips =
                FilterUiModelMapper.mapCurrenciesAsFilterToCurrencyChips(freshCurrencyFilter.allCurrenciesAsFilter)
            
            FilterInstance.filters.value?.let { oldFiltersModel ->
                FilterInstance.filters.postValue(
                    oldFiltersModel.copy(currencyFilter = freshCurrencyFilter)
                )
                _filtersLiveData.postValue(oldFilter.copy(currencyChips = freshCurrencyChips))
            }
            
        }
    }
}
