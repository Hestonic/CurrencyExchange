package com.example.exchanger.ui.fragments.history

import androidx.recyclerview.widget.RecyclerView
import com.example.exchanger.databinding.ItemHistoryBinding
import com.example.exchanger.ui.model.History

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