package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.HistoryUiModel

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var currencyFilterList: List<String> = listOf()

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

    inner class FilterViewHolder(private val binding: ItemCurrencyFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: String) = binding.run {
            currencyName.text = currency
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(filterList: List<String>) {
        this.currencyFilterList = filterList
        notifyDataSetChanged()
    }
}