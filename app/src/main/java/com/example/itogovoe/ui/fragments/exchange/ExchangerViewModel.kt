package com.example.itogovoe.ui.fragments.exchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.CurrencyRepository
import com.example.itogovoe.domain.repository.HistoryRepository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.mapper.ExchangerUiModelMapper
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import com.example.itogovoe.ui.model.ExchangerUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangerViewModel(
    private val currencyRepository: CurrencyRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _exchanger: MutableLiveData<ExchangerUiModel> = MutableLiveData()
    val exchanger: LiveData<ExchangerUiModel> get() = _exchanger
    
    private val _isFreshOnHistorySave = MutableLiveData<Boolean>()
    val isFreshOnHistorySave: LiveData<Boolean> get() = _isFreshOnHistorySave
    
    private val _isFreshOnTextChange = MutableLiveData<Boolean>()
    val isFreshOnTextChange: LiveData<Boolean> get() = _isFreshOnTextChange
    
    private var coefficient = 0f
    
    private fun initLoadingUiModel() {
        _exchanger.postValue(ExchangerUiModelMapper.mapLoadingExchangeUiModel())
    }
    
    fun initUiModel(args: ExchangerFragmentArgs) {
        viewModelScope.launch(Dispatchers.IO) {
            initLoadingUiModel()
            currencyRepository.getCurrencies()
            val valueParent = readCurrency(args.currencyParentName)
            val valueChild = readCurrency(args.currencyChildName)
            calculateCoefficient(valueParent, valueChild)
            _exchanger.postValue(ExchangerUiModelMapper.mapExchangeUiModel(args, coefficient))
        }
    }
    
    fun refreshUiModelValues(valueParent: Float) {
        viewModelScope.launch {
            _exchanger.value?.let { oldExchangerUiModel ->
                val refreshedExchangerUiModel =
                    ExchangerUiModelMapper.refreshUiModel(oldExchangerUiModel, valueParent, coefficient)
                _exchanger.postValue(refreshedExchangerUiModel)
            }
            
        }
    }

    fun updateCurrencies(args: ExchangerFragmentArgs) {
        viewModelScope.launch(Dispatchers.IO) {
            _exchanger.value?.let { oldExchangerUiModel ->
                initLoadingUiModel()
                currencyRepository.getCurrencies()
                val valueParentDb = readCurrency(args.currencyParentName)
                val valueChildDb = readCurrency(args.currencyChildName)
                calculateCoefficient(valueParentDb, valueChildDb)
                val freshExchangerUiModel =
                    ExchangerUiModelMapper.mapUpdateUiModel(oldExchangerUiModel, coefficient)
                _exchanger.postValue(freshExchangerUiModel)
            }
    
        }
    }
    
    fun addHistoryItem(
        nameParent: String, nameChild: String,
        valueParent: Float, valueChild: Float
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyUiModel =
                HistoryUiModelMapper.mapHistoryModel(nameParent, nameChild, valueParent, valueChild)
            historyRepository.addHistoryItem(
                HistoryUiModelMapper.mapHistoryModelToDtoModel(historyUiModel)
            )
        }
    }

    fun checkIsFreshOnHistorySave() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnHistorySave.postValue(currencyRepository.isFresh())
        }
    }
    
    fun checkIsFreshOnTextChange() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnTextChange.postValue(currencyRepository.isFresh())
        }
    }
    
    fun reverseCurrencies() {
        _exchanger.value?.let { oldExchangerUiModel ->
            val freshExchangerUiModel =
                ExchangerUiModelMapper.reverseCurrencyUiModel(oldExchangerUiModel)
            calculateCoefficient(
                freshExchangerUiModel.currencyValueParent,
                freshExchangerUiModel.currencyValueChild
            )
            _exchanger.postValue(freshExchangerUiModel)
        }
    }
    
    private fun calculateCoefficient(parentValue: Float, childValue: Float) {
        coefficient = childValue / parentValue
    }
    
    private fun readCurrency(name: String) = currencyRepository.readCurrency(name).value
}