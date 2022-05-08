package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.CurrencyChipsUiModel

class FilterAdapter : RecyclerView.Adapter<FilterViewHolder>() {

    private var currencyChipsList: List<CurrencyChipsUiModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterViewHolder {
        val binding =
            ItemCurrencyFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = currencyChipsList[position]
        holder.bind(filter)
    }

    override fun getItemCount(): Int = currencyChipsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(filterList: List<CurrencyChipsUiModel>) {
        this.currencyChipsList = filterList
        notifyDataSetChanged()
    }
}