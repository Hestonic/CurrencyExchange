package com.example.itogovoe.ui.fragments.exchange

import androidx.lifecycle.LiveData
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

    private val _currencies: MutableLiveData<List<CurrencyUiModel>> =  MutableLiveData()
    val currencies: LiveData<List<CurrencyUiModel>> = _currencies

    private val _isFreshOnHistorySave = MutableLiveData<Boolean>()
    val isFreshOnHistorySave: LiveData<Boolean> = _isFreshOnHistorySave

    private val _isFreshOnTextChange = MutableLiveData<Boolean>()
    val isFreshOnTextChange: LiveData<Boolean> = _isFreshOnTextChange

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
                _currencies.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }
        }
    }

    fun checkIsFreshOnHistorySave() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnHistorySave.postValue(repository.isFresh())
        }
    }

    fun checkIsFreshOnTextChange() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnTextChange.postValue(repository.isFresh())
        }
    }

    // TODO: Избавиться от !!
    fun calculateCrossCoefficientLive(currencyParentName: String, currencyChildName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            valueParent = repository.readCurrency(currencyParentName).value!!.toFloat()
            valueChild = repository.readCurrency(currencyChildName).value!!.toFloat()
            coefficient = valueChild / valueParent
        }
    }

}