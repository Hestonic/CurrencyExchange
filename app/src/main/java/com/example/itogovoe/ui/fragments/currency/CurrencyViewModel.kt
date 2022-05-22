package com.example.itogovoe.ui.fragments.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.CurrencyRepository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(private val currencyRepository: CurrencyRepository) : ViewModel() {

    data class ExchangeNavArgs(
        val currencyParentName: String,
        val currencyChildName: String,
    )

    private val _currenciesLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val currenciesLiveData: LiveData<List<CurrencyUiModel>> get() = _currenciesLiveData

    private val _errorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<Boolean> get() = _errorLiveData

    private val eventChannel = Channel<ExchangeNavArgs>()
    val eventFlow get() = eventChannel.receiveAsFlow()

    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencies = currencyRepository.getCurrencies()
            if (currencies.isNotEmpty()) {
                sortCurrencyUiModelList(CurrencyUiModelMapper.mapDomainModelToUiModel(currencies))
            } else _errorLiveData.postValue(true)
        }
    }

    private fun sortCurrencyUiModelList(currencyUiModelList: List<CurrencyUiModel>?) {
        _currenciesLiveData.postValue(currencyUiModelList
            ?.sortedByDescending { currency -> currency.lastUsedAt }
            ?.sortedByDescending { currency -> currency.isFavourite }
            ?.toMutableList())
    }

    private fun isCheckedSort(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val currenciesList = _currenciesLiveData.value?.toMutableList()
            currenciesList?.remove(currencyUiModel)
            val checkedCurrencyUiModel =
                CurrencyUiModelMapper.mapCurrencyUiModelIsChecked(currencyUiModel)
            currenciesList?.add(0, checkedCurrencyUiModel)
            _currenciesLiveData.postValue(currenciesList)
        }
    }

    fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrencyIsFavourite(name, isFavourite)
            getCurrencies()
        }
    }

    fun handleSingleClick(currencyChildName: String) {
        val currencyList = _currenciesLiveData.value
        if (currencyList != null)
            when {
                currencyList[0].isChecked ->
                    postNavigationArgs(currencyList[0].name, currencyChildName)

                currencyList[0].isFavourite ->
                    postNavigationArgs(currencyList[0].name, currencyChildName)

                else -> currencyList.forEach { currency ->
                    if (currency.name == "RUB" || currency.name == "EUR" || currency.name == "USD") {
                        postNavigationArgs(currency.name, currencyChildName)
                        return
                    }
                }
            }
    }

    fun handleLongClick(currencyUiModel: CurrencyUiModel) {
        val currencyList = _currenciesLiveData.value
        if (currencyList != null) {
            if (!currencyList[0].isChecked) isCheckedSort(currencyUiModel)
            else updateCurrencyLastUsedAt(currencyUiModel.name)
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
            val foundCurrenciesUiModel = CurrencyUiModelMapper.mapDomainModelToUiModel(
                currencyRepository.searchCurrenciesDatabase(searchQuery)
            )
            sortCurrencyUiModelList(foundCurrenciesUiModel)
        }
    }
}

