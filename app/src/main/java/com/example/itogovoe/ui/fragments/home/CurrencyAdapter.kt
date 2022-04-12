package com.example.itogovoe.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.R
import com.example.itogovoe.databinding.ItemCurrencyBinding
import com.example.itogovoe.ui.model.CurrencyUiModel
import java.util.*


class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.HomeViewHolder>() {

    var currencyList: List<CurrencyUiModel> = emptyList()
    private var base = "EUR"
    private var currencyParentName: String? = null
    private var currencyChildName = "EUR"
    private var currencyParentValue = 1.0
    private var currencyChildValue = 1.0
    private var selectedPosition = -1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyAdapter.HomeViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyAdapter.HomeViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.bind(currency)
    }

    override fun getItemCount(): Int = currencyList.size

    inner class HomeViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyUiModel: CurrencyUiModel) = binding.run {
            currency.text = currencyUiModel.name

            if (currencyUiModel.isNotChecked) {
                currencyLayout.setBackgroundResource(R.drawable.round_bg_currency)
            } else {
                currencyLayout.setBackgroundResource(R.drawable.round_bg_currency_selected)
            }

            currencyLayout.setOnLongClickListener {
                if (selectedPosition == -1 && currencyParentName != base) {
                    base = currencyUiModel.name
                    currencyParentName = currencyUiModel.name
                    currencyParentValue = currencyUiModel.value
                    currencyUiModel.isNotChecked = false
                    selectedPosition = absoluteAdapterPosition
                    swapItem(selectedPosition, 0)
                    Toast.makeText(itemView.context, "Выбрана валюта $base", Toast.LENGTH_SHORT)
                        .show()
                } else if (selectedPosition == -1 && currencyParentName == base) {
                    Toast.makeText(
                        itemView.context,
                        "$base уже выбрана по умолчанию",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (absoluteAdapterPosition == 0) {
                    base = "EUR"
                    currencyParentName = null
                    currencyChildName = "EUR"
                    currencyUiModel.isNotChecked = true
                    swapItem(0, selectedPosition)
                    selectedPosition = -1
                    Toast.makeText(
                        itemView.context,
                        "Вернули значение по умолчанию",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                notifyDataSetChanged()
                return@setOnLongClickListener true
            }

            currencyLayout.setOnClickListener {
                if (currencyParentName == null) {
                    val action = HomeFragmentDirections.actionHomeFragmentToExchangeFragment(
                        currencyChildName = currencyUiModel.name,
                        currencyChildValue = currencyUiModel.value.toFloat(),
                    )
                    currencyLayout.findNavController().navigate(action)
                } else {
                    val action = HomeFragmentDirections.actionHomeFragmentToExchangeFragment(
                        currencyChildName = currencyUiModel.name,
                        currencyChildValue = currencyUiModel.value.toFloat(),
                        currencyParentName = currencyParentName!!,
                        currencyParentValue = currencyParentValue.toFloat()
                    )
                    currencyLayout.findNavController().navigate(action)
                }
            }
        }

        private fun swapItem(fromPosition: Int, toPosition: Int) {
            Collections.swap(currencyList, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
        }
    }
}