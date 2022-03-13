package com.example.itogovoe.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.data.api.Currency
import com.example.itogovoe.domain.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    // TODO("В дальнейшем заменить Response<Currency> на CurrencyUiModel")
    val myResponce: MutableLiveData<Response<Currency>> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch {
            val responce = repository.getCurrencies()
            myResponce.postValue(responce)
            /*repository.getCurrencies().let {
                myResponce.postValue(CurrencyUiModelMapper.mapDomainModelToUiModel(it))
            }*/

        }
    }
}