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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    private val _filtersLiveData: MutableLiveData<FilterUiModel> = MutableLiveData()
    val filterLiveData: LiveData<FilterUiModel> get() = _filtersLiveData

    /*fun getFilterItems() {
        when (FilterInstance.timeFilter) {
            is TimeFilter.AllTime -> setAllTimeFilter()
            is TimeFilter.Month -> setMonthFilter()
            is TimeFilter.Week -> setWeekFilter()
            is TimeFilter.Range -> setRangeFilter()
        }
    }*/

    fun initFilterUiModel() {
        viewModelScope.launch(Dispatchers.IO) {
            _filtersLiveData.postValue(FilterUiModelMapper.mapFilterUiModel(getCurrenciesFilter()))
        }
    }

    private fun getCurrenciesFilter(): List<CurrencyChipsUiModel> {
        historyRepository.getHistory().let { historyDtoModelList ->
            return FilterUiModelMapper.mapHistoryDomainModelToCurrencyChipsUiModel(
                historyDtoModelList
            )
        }
    }

    fun setTimeFilter(name: String) {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            val timeFilterUiModelList = mutableListOf<TimeFilterUiModel>()
            oldFilters?.timeFilters?.forEach { filter ->
                if (filter.name == name) timeFilterUiModelList.add(TimeFilterUiModel(name, true))
                else timeFilterUiModelList.add(TimeFilterUiModel(filter.name, false))
            }
            _filtersLiveData.postValue(oldFilters?.copy(timeFilters = timeFilterUiModelList))
        }
        setTimeToFilterInstance(name)
    }

    private fun setTimeToFilterInstance(name: String) {
        when (name) {
            "За всё время" -> FilterInstance.timeFilter = TimeFilter.AllTime
            "За месяц" -> FilterInstance.timeFilter = TimeFilter.Month
            "За неделю" -> FilterInstance.timeFilter = TimeFilter.Week
        }
    }
        /*private fun setAllTimeFilter() {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            _filtersLiveData.postValue(
                oldFilters?.copy(timeFilters = TimeFilterUiModel("За всё время", true))
            )
        }


    private fun setMonthFilter() {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            _filtersLiveData.postValue(
                oldFilters?.copy(timeFilters = TimeFilterUiModel("За месяц", true))
            )
        }
    }

    private fun setWeekFilter() {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            _filtersLiveData.postValue(
                oldFilters?.copy(timeFilters = TimeFilterUiModel("За неделю", true))
            )
        }
    }

    private fun setRangeFilter() {
    }*/



}


// TODO: не забыть удалить
/*private fun setMonthFilter(currencyChipsUiModelList: List<CurrencyChipsUiModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            val filterUiModel = FilterUiModel(
                timeFilters = listOf(
                    TimeFilterUiModel("За всё время", true),
                    TimeFilterUiModel("За неделю", false),
                    TimeFilterUiModel("За месяц", false),
                ),
                timeRange = TimeRangeUiModel("Выбрать дату", LocalDateTime.now().toString(), false),
                currencyChips = currencyChipsUiModelList,
            )
            _filtersUi.postValue(filterUiModel)
        }
    }

    private fun setWeekFilter(currencyChipsUiModelList: List<CurrencyChipsUiModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            val filterUiModel = FilterUiModel(
                timeFilters = listOf(
                    TimeFilterUiModel("За всё время", true),
                    TimeFilterUiModel("За неделю", false),
                    TimeFilterUiModel("За месяц", false),
                ),
                timeRange = TimeRangeUiModel("Выбрать дату", LocalDateTime.now().toString(), false),
                currencyChips = currencyChipsUiModelList,
            )
            _filtersUi.postValue(filterUiModel)
        }
    }

    private fun setRangeFilter(currencyChipsUiModelList: List<CurrencyChipsUiModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            val filterUiModel = FilterUiModel(
                timeFilters = listOf(
                    TimeFilterUiModel("За всё время", true),
                    TimeFilterUiModel("За неделю", false),
                    TimeFilterUiModel("За месяц", false),
                ),
                timeRange = TimeRangeUiModel("Выбрать дату", LocalDateTime.now().toString(), false),
                currencyChips = currencyChipsUiModelList,
            )
            _filtersUi.postValue(filterUiModel)
        }
    }*/