package com.example.itogovoe.ui.fragments.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrencyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CurrencyViewModel(private val repository: Repository) : ViewModel() {

    val itemsLiveData: MutableLiveData<List<CurrencyUiModel>> = MutableLiveData()
    private var currencyUiModelSortedMutableList: MutableList<CurrencyUiModel>? = null

    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()?.let {
                sortCurrencyUiModelList(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
                itemsLiveData.postValue(currencyUiModelSortedMutableList)
            }
        }
    }

    private fun sortCurrencyUiModelList(currencyUiModelList: List<CurrencyUiModel>?) {
        currencyUiModelSortedMutableList = currencyUiModelList
            ?.sortedByDescending { currency -> currency.lastUsedAt }
            ?.sortedByDescending { currency -> currency.isFavourite }
            ?.toMutableList()
    }


    fun isCheckedSort(currencyUiModel: CurrencyUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyUiModelSortedMutableList?.remove(currencyUiModel)
            val checkedCurrencyUiModel = CurrencyUiModel(
                isChecked = true,
                isFavourite = currencyUiModel.isFavourite,
                lastUsedAt = currencyUiModel.lastUsedAt,
                name = currencyUiModel.name,
                value = currencyUiModel.value
            )
            currencyUiModelSortedMutableList?.add(0, checkedCurrencyUiModel)
            itemsLiveData.postValue(currencyUiModelSortedMutableList)
        }
    }


    fun updateCurrencyIsFavourite(name: String, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val currenciesTableItem = repository.readCurrency(name)
            repository.updateCurrency(
                CurrenciesEntity(
                    name = name,
                    value = currenciesTableItem.value,
                    createdAt = currenciesTableItem.createdAt,
                    updatedAt = currenciesTableItem.updatedAt,
                    lastUsedAt = currenciesTableItem.lastUsedAt,
                    isFavourite = isFavourite
                )
            )
        }
    }

    fun updateCurrencyLastUsedAt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currenciesTableItem = repository.readCurrency(name)
            repository.updateCurrency(
                CurrenciesEntity(
                    name = name,
                    value = currenciesTableItem.value,
                    createdAt = currenciesTableItem.createdAt,
                    updatedAt = currenciesTableItem.updatedAt,
                    lastUsedAt = LocalDateTime.now(),
                    isFavourite = currenciesTableItem.isFavourite
                )
            )
        }
    }
}