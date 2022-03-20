package com.example.itogovoe.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyBinding
import com.example.itogovoe.ui.model.CurrencyUiModel

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.HomeViewHolder>() {

    var currencyList: List<CurrencyUiModel> = emptyList()

    inner class HomeViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyUiModel: CurrencyUiModel) = binding.run {
            currency.text = currencyUiModel.name
        }
    }

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

}
