package com.example.itogovoe.ui.fragments.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itogovoe.domain.repository.Repository

class FilterViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java))
            return FilterViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}