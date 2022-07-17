package com.example.itogovoe.ui.fragments.history

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.R
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.HistoryChips

class HistoryChipsViewHolder(private val binding: ItemCurrencyFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(filterChips: HistoryChips) = binding.run {
        currencyName.text = filterChips.name
        currencyFilterLayout.isVisible = filterChips.isVisible
        currencyFilterLayout.setBackgroundResource(R.drawable.round_bg_filter_selected)
    }
}
