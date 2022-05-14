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

        // TODO: Filter
        /*if (FilterInstance.selectedCurrencies.contains(currencyName.text.toString()))
            currencyFilterLayout.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else currencyFilterLayout.setBackgroundResource(R.drawable.round_bg_filter)


        binding.currencyFilterLayout.setOnClickListener {
            if (FilterInstance.selectedCurrencies.contains(currencyName.text.toString()))
                FilterInstance.selectedCurrencies.remove(currencyName.text.toString())
            else FilterInstance.selectedCurrencies.add(currencyName.text.toString())
//                Log.d("filter_adapter_tag", FilterInstance.selectedCurrencies.toString())
            notifyDataSetChanged()
        }*/
    }
}