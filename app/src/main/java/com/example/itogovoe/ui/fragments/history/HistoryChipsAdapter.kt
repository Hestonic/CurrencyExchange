package com.example.itogovoe.ui.fragments.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.HistoryChips

class HistoryChipsAdapter : RecyclerView.Adapter<HistoryChipsViewHolder>() {
    
    private var historyList: List<HistoryChips> = emptyList()
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryChipsViewHolder {
        val binding =
            ItemCurrencyFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryChipsViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: HistoryChipsViewHolder, position: Int) {
        historyList.getOrNull(position)?.let {
            holder.bind(it)
        }
    }
    
    override fun getItemCount(): Int = historyList.size
    
    @SuppressLint("NotifyDataSetChanged")
    fun setData(historyChips: List<HistoryChips>) {
        this.historyList = historyChips
        notifyDataSetChanged()
    }
}
