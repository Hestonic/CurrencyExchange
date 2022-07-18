package com.example.exchanger.ui.fragments.currency

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.exchanger.R
import com.example.exchanger.databinding.ItemCurrencyBinding
import com.example.exchanger.ui.model.CurrencyUiModel

class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("NotifyDataSetChanged")
    fun bind(currencyUiModel: CurrencyUiModel, listener: CurrencyPassClick) = binding.run {
        currency.text = currencyUiModel.name

        if (currencyUiModel.isFavourite) star.setBackgroundResource(R.drawable.ic_star_rate)
        else star.setBackgroundResource(R.drawable.ic_star_outline)

        if (currencyUiModel.isChecked) currencyLayout.setBackgroundResource(R.drawable.round_bg_currency_selected)
        else currencyLayout.setBackgroundResource(R.drawable.round_bg_currency)

        star.setOnClickListener {
            if (currencyUiModel.isFavourite)
                listener.passIsFavouriteClick(currencyUiModel.name, false)
            else listener.passIsFavouriteClick(currencyUiModel.name, true)
        }

        currencyLayout.setOnClickListener {
            listener.passClick(currencyUiModel.name)
        }

        currencyLayout.setOnLongClickListener {
            listener.passLongClick(currencyUiModel)
            true
        }
    }
}