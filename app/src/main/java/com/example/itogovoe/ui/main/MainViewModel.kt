package com.example.itogovoe.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.data.api.CurrencyR
import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.domain.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    // TODO("В дальнейшем заменить Response<Currency> на CurrencyUiModel")
    val myResponce: MutableLiveData<Currencies> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch {
            val responce = repository.getCurrencies()
            myResponce.postValue(responce)
        }
    }
}