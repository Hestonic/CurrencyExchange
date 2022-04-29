package com.example.itogovoe.ui.fragments.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: Repository) : ViewModel() {

    private val _itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    val itemsLiveData: LiveData<List<CurrencyUiModel>> = _itemsLiveData


    private var currencyUiSorted: MutableList<CurrencyUiModel>? = null

    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()?.let {
                sortCurrencyUiModelList(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
                _itemsLiveData.postValue(currencyUiSorted)
            }
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
            repository.updateCurrencyIsFavourite(name, isFavourite)
        }
    }

    fun updateCurrencyLastUsedAt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCurrencyLastUsedAt(name)
        }
    }
}