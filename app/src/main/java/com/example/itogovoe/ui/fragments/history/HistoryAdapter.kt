package com.example.itogovoe.ui.fragments.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemHistoryBinding
import com.example.itogovoe.ui.model.HistoryUiModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var historyList: List<HistoryUiModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int = historyList.size

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryUiModel) = binding.run {
            binding.date.text = history.date
            binding.currencyNameChild.text = history.currencyNameChild
            binding.currencyValueChild.text = history.currencyValueChild.toString()
            binding.currencyNameParent.text = history.currencyNameParent
            binding.currencyValueParent.text = history.currencyValueParent.toString()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(historyEntity: List<HistoryUiModel>) {
        this.historyList = historyEntity
        notifyDataSetChanged()
    }
}