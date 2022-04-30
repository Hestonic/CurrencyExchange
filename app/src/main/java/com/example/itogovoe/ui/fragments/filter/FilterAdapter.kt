package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyFilterBinding
import com.example.itogovoe.ui.model.CurrencyChipsUiModel

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var CurrencyChipsList: List<CurrencyChipsUiModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterViewHolder {
        val binding =
            ItemCurrencyFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = CurrencyChipsList[position]
        holder.bind(filter)
    }

    override fun getItemCount(): Int = CurrencyChipsList.size

    inner class FilterViewHolder(private val binding: ItemCurrencyFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(currency: CurrencyChipsUiModel) = binding.run {
            currencyName.text = currency.name

            // TODO: Filter
            /*if (FilterInstance.selectedCurrencies.contains(currencyName.text.toString()))
                currencyFilterLayout.setBackgroundResource(R.drawable.round_bg_filter_selected)
            else currencyFilterLayout.setBackgroundResource(R.drawable.round_bg_filter)


            binding.currencyFilterLayout.setOnClickListener {
                if (FilterInstance.selectedCurrencies.contains(currencyName.text.toString()))
                    FilterInstance.selectedCurrencies.remove(currencyName.text.toString())
                else FilterInstance.selectedCurrencies.add(currencyName.text.toString())
//                Log.d("filter_adapter_tag", FilterInstance.selectedCurrencies.toString())
                notifyDataSetChanged()
            }*/
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(filterList: List<CurrencyChipsUiModel>) {
        this.CurrencyChipsList = filterList
        notifyDataSetChanged()
    }
}