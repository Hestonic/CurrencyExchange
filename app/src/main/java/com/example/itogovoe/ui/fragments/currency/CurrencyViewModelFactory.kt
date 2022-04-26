package com.example.itogovoe.ui.fragments.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itogovoe.domain.repository.Repository

class CurrencyViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java))
            return CurrencyViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}