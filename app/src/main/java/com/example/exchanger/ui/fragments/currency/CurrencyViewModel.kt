package com.example.exchanger.ui.fragments.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchanger.domain.repository.CurrencyRepository
import com.example.exchanger.ui.mapper.CurrencyUiModelMapper
import com.example.exchanger.ui.model.CurrenciesUiModel
import com.example.exchanger.ui.model.CurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(private val currencyRepository: CurrencyRepository) : ViewModel() {
    
    data class ExchangeNavArgs(
        val currencyParentName: String,
        val currencyChildName: String,
    )
    
    private val _currenciesLiveData: MutableLiveData<CurrenciesUiModel> = MutableLiveData()
    val currenciesLiveData: LiveData<CurrenciesUiModel> get() = _currenciesLiveData
    
    private val eventChannel = Channel<ExchangeNavArgs>()
    val eventFlow get() = eventChannel.receiveAsFlow()
    
    fun initUiModel() {
        _currenciesLiveData.postValue(CurrencyUiModelMapper.mapCurrencyUiModel())
    }
    
    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencies = currencyRepository.getCurrencies()
            if (currencies.isNotEmpty()) {
                val currenciesUiModel = CurrencyUiModelMapper.mapDomainModelToUiModel(currencies)
                sortCurrencyUiModelList(currenciesUiModel)
            } else _currenciesLiveData.postValue(CurrencyUiModelMapper.mapCurrencyError())
        }
    }
    
    private fun sortCurrencyUiModelList(currenciesUiModelList: CurrenciesUiModel) {
        val sortedCurrencies = currenciesUiModelList.currencies
            .sortedByDescending { currency -> currency.lastUsedAt }
            .sortedByDescending { currency -> currency.isFavourite }
        _currenciesLiveData.postValue(currenciesUiModelList.copy(currencies = sortedCurrencies))
    }

    fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrencyIsFavourite(name, isFavourite)
            getCurrencies()
        }
    }

    fun handleSingleClick(currencyChildName: String) {
        _currenciesLiveData.value?.currencies?.let { currencyList ->
            currencyList.firstOrNull()?.let { firstCurrency ->
                when {
                    firstCurrency.isChecked ->
                        postNavigationArgs(firstCurrency.name, currencyChildName)
            
                    firstCurrency.isFavourite ->
                        postNavigationArgs(firstCurrency.name, currencyChildName)
            
                    else -> currencyList.forEach { currency ->
                        if (currency.name == "RUB" || currency.name == "EUR" || currency.name == "USD")
                            postNavigationArgs(currency.name, currencyChildName)
                    }
                }
            }
        }
    }
    
    fun handleLongClick(clickedCurrency: CurrencyUiModel) {
        _currenciesLiveData.value?.currencies?.let { currencyList ->
            currencyList.firstOrNull()?.let { firstCurrency ->
                if (firstCurrency.isChecked && firstCurrency.name == clickedCurrency.name)
                    updateCurrencyLastUsedAt(firstCurrency.name)
                else if (!firstCurrency.isChecked)
                    isCheckedSort(clickedCurrency)
            }
        }
    }
    
    
    private fun isCheckedSort(oldCurrency: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _currenciesLiveData.value?.let { oldCurrencies ->
                val freshCurrencies =
                    CurrencyUiModelMapper.mapCurrencyUiModelIsChecked(oldCurrencies, oldCurrency)
                _currenciesLiveData.postValue(freshCurrencies)
            }
        }
    }
    
    private fun postNavigationArgs(currencyParentName: String, currencyChildName: String) {
        updateCurrencyLastUsedAt(currencyParentName)
        updateCurrencyLastUsedAt(currencyChildName)
        navigationWithArgs(currencyParentName, currencyChildName)
    }
    
    private fun updateCurrencyLastUsedAt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrencyLastUsedAt(name)
            getCurrencies()
        }
    }

    private fun navigationWithArgs(currencyParentName: String, currencyChildName: String) =
        viewModelScope.launch {
            eventChannel.send(ExchangeNavArgs(currencyParentName, currencyChildName))
        }

    fun searchCurrencies(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.searchCurrenciesDatabase(searchQuery).let { currencyDto ->
                val foundCurrenciesUiModel =
                    CurrencyUiModelMapper.mapDomainModelToUiModel(currencyDto)
                sortCurrencyUiModelList(foundCurrenciesUiModel)
            }
        }
    }
}

