package com.example.itogovoe.ui.fragments.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itogovoe.domain.repository.CurrencyRepository
import com.example.itogovoe.domain.repository.HistoryRepository

class ExchangerViewModelFactory(
    private val currencyRepository: CurrencyRepository,
    private val historyRepository: HistoryRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangerViewModel::class.java))
            return ExchangerViewModel(currencyRepository, historyRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}