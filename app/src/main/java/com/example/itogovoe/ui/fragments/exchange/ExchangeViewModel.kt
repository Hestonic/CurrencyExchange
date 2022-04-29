package com.example.itogovoe.ui.fragments.exchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import com.example.itogovoe.ui.model.HistoryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangeViewModel(private val repository: Repository) : ViewModel() {

    val itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val isFreshOnHistorySave = MutableLiveData<Boolean>()
    val isFreshOnTextChange = MutableLiveData<Boolean>()
    var coefficient: Float = 0f
    private var valueParent: Float = 0f
    private var valueChild: Float = 0f

    fun addHistoryItem(historyUiModel: HistoryUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistoryItem(HistoryUiModelMapper.mapHistoryUiModelToDomainModel(historyUiModel))
        }
    }

    fun getCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()?.let {
                itemsLiveData.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }
        }
    }

    /*fun isFresh(): LiveData<Boolean> {
        val isFresh = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            isFresh.postValue(repository.isFresh())
        }
        return isFresh
    }*/

    fun checkIsFreshOnHistorySave() {
        viewModelScope.launch(Dispatchers.IO) {
            isFreshOnHistorySave.postValue(repository.isFresh())
        }
    }

    fun checkIsFreshOnTextChange() {
        viewModelScope.launch(Dispatchers.IO) {
            isFreshOnTextChange.postValue(repository.isFresh())
        }
    }

    /*fun checkIsFresh() {
        viewModelScope.launch(Dispatchers.IO) {
            dataIsFresh = repository.isFresh()
        }
    }*/

    // TODO: Избавиться от !!
    fun calculateCrossCoefficientLive(currencyParentName: String, currencyChildName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            valueParent = repository.readCurrency(currencyParentName).value!!.toFloat()
            valueChild = repository.readCurrency(currencyChildName).value!!.toFloat()
            coefficient = valueChild / valueParent
        }
    }

}