package com.example.exchanger.ui.fragments.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchanger.domain.repository.HistoryRepository

class FilterViewModelFactory(private val historyRepository: HistoryRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java))
            return FilterViewModel(historyRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}