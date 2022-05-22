package com.example.itogovoe.ui.fragments.history

import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemHistoryBinding
import com.example.itogovoe.ui.model.History
import com.example.itogovoe.ui.model.HistoryUiModel

class HistoryViewHolder(private val binding: ItemHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(history: History) = binding.run {
        binding.date.text = history.date
        binding.currencyNameChild.text = history.currencyNameChild
        binding.currencyValueChild.text = history.currencyValueChild.toString()
        binding.currencyNameParent.text = history.currencyNameParent
        binding.currencyValueParent.text = history.currencyValueParent.toString()
    }
}