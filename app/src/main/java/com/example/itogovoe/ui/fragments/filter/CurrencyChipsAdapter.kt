package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.CurrencyChipsUiModel

class CurrencyChipsAdapter : RecyclerView.Adapter<CurrencyChipsViewHolder>() {

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
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = currencyChipsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CurrencyChipsUiModel>) {
        this.currencyChipsList = data
        notifyDataSetChanged()
    }
}