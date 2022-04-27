package com.example.itogovoe.ui.fragments.currency

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.R
import com.example.itogovoe.databinding.ItemCurrencyBinding
import com.example.itogovoe.ui.model.CurrencyUiModel
import java.util.*


class CurrencyAdapter(private val listener: CurrencyPassClick) :
    RecyclerView.Adapter<CurrencyAdapter.HomeViewHolder>() {

    var currencyList: List<CurrencyUiModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyAdapter.HomeViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyAdapter.HomeViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.bind(currency)
    }

    override fun getItemCount(): Int = currencyList.size

    inner class HomeViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(currencyUiModel: CurrencyUiModel) = binding.run {
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
                    currencyList[0].isChecked -> {
                        listener.passClick(
                            currencyList[0].name, currencyList[0].value,
                            currencyUiModel.name, currencyUiModel.value,
                        )
                    }
                    currencyList[0].isFavourite -> {
                        listener.passClick(
                            currencyList[0].name, currencyList[0].value,
                            currencyUiModel.name, currencyUiModel.value,
                        )
                    }
                    else -> {
                        currencyList.forEach { currency ->
                            if (currency.name == "RUB") {
                                listener.passClick(
                                    currency.name, currency.value,
                                    currencyUiModel.name, currencyUiModel.value,
                                )
                            }
                        }
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

    @SuppressLint("NotifyDataSetChanged")
    fun setData(currencyList: List<CurrencyUiModel>) {
        this.currencyList = currencyList
        notifyDataSetChanged()
    }
}