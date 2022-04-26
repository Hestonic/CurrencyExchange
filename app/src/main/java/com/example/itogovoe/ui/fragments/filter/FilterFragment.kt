package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.databinding.FragmentFilterBinding
import com.example.itogovoe.ui.fragments.currency.CurrencyViewModel
import com.example.itogovoe.ui.fragments.currency.CurrencyViewModelFactory
import java.util.*

class FilterFragment : Fragment() {

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var binding: FragmentFilterBinding
    private val adapter = FilterAdapter()

    private var year = 0
    private var month = 0
    private var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = CurrencyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]
        viewModel.getFilterItems()
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        //renderState(FilterInstance)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.filterItems.observe(viewLifecycleOwner) { filterList -> adapter.setData(filterList) }

        binding.filterAllTime.setOnClickListener {
            // TODO:
        }

        binding.filterMonth.setOnClickListener {
            // TODO:
        }

        binding.filterWeek.setOnClickListener {
            // TODO:
        }

        getDateCalendar()
        //dateChooser()

        return binding.root
    }

    private fun getDateCalendar() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    /*@SuppressLint("SetTextI18n")
    private fun dateChooser() {
        binding.chooseDateFrom.setOnClickListener {
            DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
                binding.chooseDateFrom.text = "$mYear-$mMonth-$mDay"
                year = mYear
                month = mMonth
                day = mDay
                // TODO:
                renderState(FilterInstance)
            }, year, month, day).show()
        }

        binding.chooseDateTo.setOnClickListener {
            DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
                binding.chooseDateTo.text = "$mYear-$mMonth-$mDay"
                year = mYear
                month = mMonth
                day = mDay
                FilterInstance.dateTo = LocalDateTime.of(year, month + 1, day, 23, 50, 59)
                renderState(FilterInstance)
            }, year, month, day).show()
        }
    }*/

    /*private fun renderState(state: FilterInstance) = with(binding) {
        if (state.allTimeFilter) filterAllTime.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterAllTime.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.monthFilter) filterMonth.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterMonth.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.weekFilter) filterWeek.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterWeek.setBackgroundResource(R.drawable.round_bg_filter)

        binding.chooseDateTo.text = FilterInstance.dateTo.toLocalDate().toString()
        if (FilterInstance.dateFrom != null)
            binding.chooseDateFrom.text = FilterInstance.dateFrom?.toLocalDate().toString()
        else
            binding.chooseDateFrom.text = "Выбрать дату"
    }*/
}