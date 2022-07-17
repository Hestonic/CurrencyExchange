package com.example.exchanger.ui.fragments.filter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.exchanger.R
import com.example.exchanger.databinding.ItemCurrencyFilterBinding
import com.example.exchanger.ui.model.CurrencyChipsUiModel

class CurrencyChipsViewHolder(private val binding: ItemCurrencyFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("NotifyDataSetChanged")
    fun bind(currency: CurrencyChipsUiModel, listener: FilterPassClick) = binding.run {
        currencyName.text = currency.name
        if (currency.isChecked) itemView.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else itemView.setBackgroundResource(R.drawable.round_bg_filter)
        itemView.setOnClickListener {
            listener.passCurrencyChipsClick(clickedElement = currency)
        }
    }
}