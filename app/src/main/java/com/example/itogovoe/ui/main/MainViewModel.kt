package com.example.itogovoe.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.data.source.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import com.example.itogovoe.ui.model.ExchangeUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    // TODO: тут происходит фильтрация данных для UI в adapter, тут будет фильтроваться isFavourite со звёздочкой
    val itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val readAllHistory: LiveData<List<HistoryEntity>> = repository.readAllHistory
    val exchangeUiModel: MutableLiveData<ExchangeUiModel> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()?.let {
                itemsLiveData.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }
        }
    }

    fun addHistoryItem(historyEntity: HistoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistoryItem(historyEntity)
        }
    }

    fun isFresh(): LiveData<Boolean> {
        val isFresh = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            isFresh.postValue(repository.isFresh())
        }
        return isFresh
    }
}