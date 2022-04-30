package com.example.itogovoe.ui.fragments.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.main.FilterInstance
import com.example.itogovoe.ui.main.TimeFilter
import com.example.itogovoe.ui.mapper.FilterUiModelMapper
import com.example.itogovoe.ui.model.FilterUiModel
import com.example.itogovoe.ui.model.TimeFilterUiModel
import com.example.itogovoe.ui.model.TimeRangeUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class FilterViewModel(private val repository: Repository) : ViewModel() {

    private val _filtersLiveData: MutableLiveData<FilterUiModel> = MutableLiveData()
    val filterLiveData: LiveData<FilterUiModel> = _filtersLiveData

    fun getFilterItems() {
        if (_filtersLiveData.value == null) initFilterUiModel()
        when (FilterInstance.timeFilter) {
            is TimeFilter.AllTime -> setAllTimeFilter()
            is TimeFilter.Month -> setMonthFilter()
            is TimeFilter.Week -> setWeekFilter()
            is TimeFilter.Range -> setRangeFilter()
        }
    }

    private fun setAllTimeFilter() {
        viewModelScope.launch {
            val oldFilters = _filtersLiveData.value
            _filtersLiveData.postValue(
                oldFilters?.copy(timeFilters = TimeFilterUiModel("За всё время", true))
            )
        }
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
        TODO("Not yet implemented")
    }


    private fun initFilterUiModel() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let { historyDtoModelList ->
                val currencyChipsUiModelList =
                    FilterUiModelMapper.mapHistoryDomainModelToCurrencyChipsUiModel(
                        historyDtoModelList
                    )
                val filterUiModel = FilterUiModel(
                    timeFilters = TimeFilterUiModel("За всё время", true),
                    timeRange = TimeRangeUiModel(
                        "Выбрать дату",
                        LocalDateTime.now().toString(),
                        false
                    ),
                    currencyChips = currencyChipsUiModelList,
                )
                _filtersLiveData.postValue(filterUiModel)
            }
        }
    }
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