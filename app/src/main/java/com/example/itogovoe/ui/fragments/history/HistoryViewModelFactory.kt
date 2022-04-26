package com.example.itogovoe.ui.fragments.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itogovoe.domain.repository.Repository

class HistoryViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java))
            return HistoryViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}