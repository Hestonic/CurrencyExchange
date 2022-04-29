package com.example.itogovoe.ui.fragments.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterViewModel(private val repository: Repository) : ViewModel() {

    private val _filterItems: MutableLiveData<List<String>> = MutableLiveData()
    val filterItems: MutableLiveData<List<String>> = _filterItems

    fun getFilterItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHistory().let {
                _filterItems.postValue(HistoryUiModelMapper.mapHistoryDomainModelToFilterList(it))
            }
        }
    }
}