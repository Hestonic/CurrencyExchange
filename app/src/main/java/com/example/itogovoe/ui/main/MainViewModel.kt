package com.example.itogovoe.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    // TODO: тут происходит фильтрация данных для UI в adapter, тут будет фильтроваться isFavourite со звёздочкой
    val itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()?.let {
                itemsLiveData.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }
        }
    }
}