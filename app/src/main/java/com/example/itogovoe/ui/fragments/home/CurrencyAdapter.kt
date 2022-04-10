package com.example.itogovoe.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.databinding.ItemCurrencyBinding
import com.example.itogovoe.ui.model.CurrencyUiModel


class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.HomeViewHolder>() {

    var currencyList: List<CurrencyUiModel> = emptyList()

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

            currencyLayout.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToExchangeFragment(
                    currencyChildName = currencyUiModel.name,
                    currencyChildValue = currencyUiModel.value.toFloat(),
                )
                currencyLayout.findNavController().navigate(action)


                /*if (currencyUiModel.isNotChecked) {
                    currencyLayout.setBackgroundResource(R.drawable.round_bg_currency_selected)
                    currencyUiModel.isNotChecked = false
                } else {
                    currencyLayout.setBackgroundResource(R.drawable.round_bg_currency)
                    notifyDataSetChanged()
                }*/
            }
        }

        /*private fun swapItem(fromPosition: Int, toPosition: Int) {
            Collections.swap(currencyList, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
        }*/
    }
}


// TODO: удалить как доделаю
/*if (firstItemPosition == -1) {
                    swapItem(position, 0)
                    currencyLayout.setBackgroundResource(R.drawable.round_bg_currency_selected)
                    firstItemPosition = position
                    checkedItemName = currencyUiModel.name
                    currencyParentName = currencyUiModel.name
                    currencyParentValue = currencyUiModel.value.toFloat()
                } else if (checkedItemName == binding.currency.text) {
                    swapItem(0, firstItemPosition)
                    currencyLayout.setBackgroundResource(R.drawable.round_bg_currency)
                    firstItemPosition = -1
                    checkedItemName = ""
                } else {
                    firstItemPosition = -1
                    checkedItemName = ""
                    currencyChildName = currencyUiModel.name
                    currencyChildValue = currencyUiModel.value.toFloat()
                    val action = HomeFragmentDirections.actionHomeFragmentToExchangeFragment(
                        currencyParentName = currencyParentName,
                        currencyParentValue = currencyParentValue,
                        currencyChildName = currencyChildName,
                        currencyChildValue = currencyChildValue
                    )
                    currencyLayout.findNavController().navigate(action)
                }*/