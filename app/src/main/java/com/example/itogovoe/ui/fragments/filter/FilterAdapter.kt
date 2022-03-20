package com.example.itogovoe.ui.fragments.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.CurrencyUiModel

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    var filterList: List<CurrencyUiModel> = emptyList()

    inner class FilterViewHolder(private val binding: ItemCurrencyFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyUiModel: CurrencyUiModel) = binding.run {
            // TODO: вместо currencyUiModel сделать UiModel для filter

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterAdapter.FilterViewHolder {
        val binding = ItemCurrencyFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = filterList[position]
        holder.bind(filter)
    }

    override fun getItemCount(): Int = filterList.size
}