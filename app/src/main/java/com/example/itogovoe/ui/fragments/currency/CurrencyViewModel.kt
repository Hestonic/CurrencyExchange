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

    private val _itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val itemsLiveData: LiveData<List<CurrencyUiModel>> get() = _itemsLiveData

    private val _errorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<Boolean> get() = _errorLiveData

    private var currencyUiSorted: MutableList<CurrencyUiModel>? = null

    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.getCurrencies()?.let {
                sortCurrencyUiModelList(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
                _itemsLiveData.postValue(currencyUiSorted)
            } ?: _errorLiveData.postValue(true)
        }
    }

    private fun sortCurrencyUiModelList(currencyUiModelList: List<CurrencyUiModel>?) {
        currencyUiSorted = currencyUiModelList
            ?.sortedByDescending { currency -> currency.lastUsedAt }
            ?.sortedByDescending { currency -> currency.isFavourite }
            ?.toMutableList()
    }


    fun isCheckedSort(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyUiSorted?.remove(currencyUiModel)
            val checkedCurrencyUiModel = CurrencyUiModel(
                isChecked = true,
                isFavourite = currencyUiModel.isFavourite,
                lastUsedAt = currencyUiModel.lastUsedAt,
                name = currencyUiModel.name
            )
            currencyUiSorted?.add(0, checkedCurrencyUiModel)
            _itemsLiveData.postValue(currencyUiSorted)
        }
    }


    fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrencyIsFavourite(name, isFavourite)
        }
    }

    fun updateCurrencyLastUsedAt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrencyLastUsedAt(name)
        }
    }

    fun searchCurrenciesDatabase(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val responseCurrenciesDtoModel = currencyRepository.searchCurrenciesDatabase(searchQuery)
            val currenciesUiModel =
                CurrencyUiModelMapper.mapDomainModelToUiModel(responseCurrenciesDtoModel)
            sortCurrencyUiModelList(currenciesUiModel)
            _itemsLiveData.postValue(currencyUiSorted)
        }
    }
}