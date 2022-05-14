package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.R
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.TimeFilterUiModel

class TimeChipsViewHolder(private val binding: ItemCurrencyFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("NotifyDataSetChanged")
    fun bind(filter: TimeFilterUiModel, listener: FilterPassClick) = binding.run {
        currencyName.text = filter.name
        if (filter.isChecked) itemView.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else itemView.setBackgroundResource(R.drawable.round_bg_filter)
        itemView.setOnClickListener {
            listener.passChipsClick(filter.name)
        }
    }
}