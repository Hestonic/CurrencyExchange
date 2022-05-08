package com.example.itogovoe.ui.fragments.currency

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.R
import com.example.itogovoe.databinding.ItemCurrencyBinding
import com.example.itogovoe.ui.model.CurrencyUiModel

class HomeViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("NotifyDataSetChanged")
    fun bind(
        currencyUiModel: CurrencyUiModel,
        listener: CurrencyPassClick,
        currencyList: List<CurrencyUiModel>
    ) = binding.run {
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
                when {
                    currencyList[0].isChecked ->
                        listener.passClick(currencyList[0].name, currencyUiModel.name)

                    currencyList[0].isFavourite ->
                        listener.passClick(currencyList[0].name, currencyUiModel.name)

                    else -> currencyList.forEach { currency ->
                        if (currency.name == "RUB")
                            listener.passClick(currency.name, currencyUiModel.name)
                    }
                }
            }

            currencyLayout.setOnLongClickListener {
                if (!currencyList[0].isChecked) listener.passIsCheckedLongClick(currencyUiModel)
                else listener.passLongClick(currencyUiModel.name)
                true
            }
        }
}