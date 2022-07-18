package com.example.exchanger.ui.fragments.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchanger.domain.repository.HistoryRepository

class HistoryViewModelFactory(private val historyRepository: HistoryRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java))
            return HistoryViewModel(historyRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}