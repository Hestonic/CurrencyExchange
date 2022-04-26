package com.example.itogovoe.ui.fragments.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itogovoe.domain.repository.Repository

class ExchangeViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeViewModel::class.java))
            return ExchangeViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}