package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.CurrencyChipsUiModel
import com.example.itogovoe.ui.model.TimeFilterUiModel

class TimeChipsAdapter(private val listener: FilterPassClick) : RecyclerView.Adapter<TimeChipsViewHolder>() {

    private var currencyChipsList: List<TimeFilterUiModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeChipsViewHolder {
        val binding =
            ItemCurrencyFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeChipsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeChipsViewHolder, position: Int) {
        currencyChipsList.getOrNull(position)?.let {
            holder.bind(it, listener)
        }
    }

    override fun getItemCount(): Int = currencyChipsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TimeFilterUiModel>) {
        this.currencyChipsList = data
        notifyDataSetChanged()
    }
}