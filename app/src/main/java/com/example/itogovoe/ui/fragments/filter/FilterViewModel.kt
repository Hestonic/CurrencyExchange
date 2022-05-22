package com.example.itogovoe.ui.fragments.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.HistoryRepository
import com.example.itogovoe.ui.main.FilterInstance
import com.example.itogovoe.ui.main.TimeFilter
import com.example.itogovoe.ui.mapper.FilterUiModelMapper
import com.example.itogovoe.ui.model.CurrencyChipsUiModel
import com.example.itogovoe.ui.model.FilterUiModel
import com.example.itogovoe.ui.model.TimeFilterUiModel
import com.example.itogovoe.utils.Constants.Companion.FILTER_ALL_TIME
import com.example.itogovoe.utils.Constants.Companion.FILTER_MONTH
import com.example.itogovoe.utils.Constants.Companion.FILTER_WEEK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    
    private val _filtersLiveData: MutableLiveData<FilterUiModel> = MutableLiveData()
    val filterLiveData: LiveData<FilterUiModel> get() = _filtersLiveData
    
    fun initFilterUiModel(timeFilter: TimeFilter) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_filtersLiveData.value == null)
                _filtersLiveData.postValue(FilterUiModelMapper.mapFilterUiModel(getCurrenciesFilter()))
            
            when (timeFilter) {
                is TimeFilter.AllTime -> updateFilter(FILTER_ALL_TIME)
                is TimeFilter.Month -> updateFilter(FILTER_MONTH)
                is TimeFilter.Week -> updateFilter(FILTER_WEEK)
                is TimeFilter.Range -> doSomething()
            }
        }
    }
    
    private fun getCurrenciesFilter(): List<CurrencyChipsUiModel> {
        historyRepository.getHistory().let { historyDtoModelList ->
            return FilterUiModelMapper.mapHistoryDomainModelToCurrencyChipsUiModel(
                historyDtoModelList
            )
        }
    }
    
    fun updateFilter(name: String) {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            val timeFilterUiModelList = mutableListOf<TimeFilterUiModel>()
            oldFilters?.timeFilters?.forEach { filter ->
                if (filter.name == name) timeFilterUiModelList.add(TimeFilterUiModel(name, true))
                else timeFilterUiModelList.add(TimeFilterUiModel(filter.name, false))
            }
            _filtersLiveData.postValue(oldFilters?.copy(timeFilters = timeFilterUiModelList))
        }
        
    }
    
    fun setTimeFilterInInstance(name: String) {
        when (name) {
            FILTER_ALL_TIME -> FilterInstance.timeFilter.postValue(TimeFilter.AllTime)
            FILTER_MONTH -> FilterInstance.timeFilter.postValue(TimeFilter.Month)
            FILTER_WEEK -> FilterInstance.timeFilter.postValue(TimeFilter.Week)
        }
    }
    
    private fun doSomething() {
        TODO("Not yet implemented")
    }
}