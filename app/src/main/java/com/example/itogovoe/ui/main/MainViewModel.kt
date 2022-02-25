package com.example.itogovoe.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.model.Currency
import com.example.itogovoe.data.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponce: MutableLiveData<Response<Currency>> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch {
            val responce = repository.getCurrency()
            myResponce.value = responce
        }
    }
}