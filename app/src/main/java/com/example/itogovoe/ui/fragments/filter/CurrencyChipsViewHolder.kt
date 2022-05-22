package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.CurrencyChipsUiModel

class CurrencyChipsViewHolder(private val binding: ItemCurrencyFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("NotifyDataSetChanged")
    fun bind(currency: CurrencyChipsUiModel) = binding.run {
        currencyName.text = currency.name
        
        // TODO: Логика отображения CurrencyChipsUiModel и нажатий
        // TODO: Логика отображения нажатий
    }
}