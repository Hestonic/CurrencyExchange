package com.example.itogovoe.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import com.example.itogovoe.ui.model.CurrenciesUiModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    // TODO("В дальнейшем заменить Response<Currency> на CurrencyUiModel")
    val liveData: MutableLiveData<CurrenciesUiModel> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch {
            /*val responce = repository.getCurrencies()
            myResponce.postValue(responce)*/

            repository.getCurrencies()?.let {
                liveData.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }
        }
    }
}