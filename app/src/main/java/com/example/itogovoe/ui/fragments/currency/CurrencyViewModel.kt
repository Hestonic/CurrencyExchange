package com.example.itogovoe.ui.fragments.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.CurrencyRepository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(private val currencyRepository: CurrencyRepository) : ViewModel() {

    private val _currenciesLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val currenciesLiveData: LiveData<List<CurrencyUiModel>> get() = _currenciesLiveData

    private val _errorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<Boolean> get() = _errorLiveData

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


    fun isCheckedSort(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val currenciesList = _currenciesLiveData.value?.toMutableList()
            currenciesList?.remove(currencyUiModel)
            val checkedCurrencyUiModel = CurrencyUiModel(
                isChecked = true,
                isFavourite = currencyUiModel.isFavourite,
                lastUsedAt = currencyUiModel.lastUsedAt,
                name = currencyUiModel.name
            )
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

    fun updateCurrencyLastUsedAt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrencyLastUsedAt(name)
            getCurrencies()
        }
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