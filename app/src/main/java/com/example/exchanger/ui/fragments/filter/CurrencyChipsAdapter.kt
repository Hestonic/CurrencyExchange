package com.example.exchanger.ui.fragments.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exchanger.databinding.ItemCurrencyFilterBinding
import com.example.exchanger.ui.model.CurrencyChipsUiModel

class CurrencyChipsAdapter(private val listener: FilterPassClick) : RecyclerView.Adapter<CurrencyChipsViewHolder>() {

    private var currencyChipsList: List<CurrencyChipsUiModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyChipsViewHolder {
        val binding =
            ItemCurrencyFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyChipsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyChipsViewHolder, position: Int) {
        currencyChipsList.getOrNull(position)?.let {
            holder.bind(it, listener)
        }
    }

    override fun getItemCount(): Int = currencyChipsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CurrencyChipsUiModel>) {
        this.currencyChipsList = data
        notifyDataSetChanged()
    }
}