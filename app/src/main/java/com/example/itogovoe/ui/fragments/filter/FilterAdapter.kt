package com.example.itogovoe.ui.fragments.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    var currencyFilterList: MutableList<String> = mutableListOf()

    inner class FilterViewHolder(private val binding: ItemCurrencyFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: String) = binding.run {
            currencyName.text = currency
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterViewHolder {
        val binding =
            ItemCurrencyFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = currencyFilterList[position]
        holder.bind(filter)
    }

    override fun getItemCount(): Int = currencyFilterList.size
}