package com.example.exchanger.ui.fragments.currency

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exchanger.databinding.ItemCurrencyBinding
import com.example.exchanger.ui.model.CurrencyUiModel


class CurrencyAdapter(private val listener: CurrencyPassClick) :
    RecyclerView.Adapter<CurrencyViewHolder>() {
    
    private var currencyList: List<CurrencyUiModel> = listOf()
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        currencyList.getOrNull(position)?.let {
            holder.bind(it, listener)
        }
    }

    override fun getItemCount(): Int = currencyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(currencyList: List<CurrencyUiModel>) {
        this.currencyList = currencyList
        notifyDataSetChanged()
    }
}