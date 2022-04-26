package com.example.itogovoe.ui.fragments.exchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangeViewModel(private val repository: Repository) : ViewModel() {

    val itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()

    fun addHistoryItem(historyEntity: HistoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistoryItem(historyEntity)
        }
    }

    fun getCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()?.let {
                itemsLiveData.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }
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