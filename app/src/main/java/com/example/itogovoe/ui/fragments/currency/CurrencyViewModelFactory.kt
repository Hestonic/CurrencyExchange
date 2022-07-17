package com.example.itogovoe.ui.fragments.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itogovoe.domain.repository.CurrencyRepository

class CurrencyViewModelFactory(private val currencyRepository: CurrencyRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java))
            return CurrencyViewModel(currencyRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}