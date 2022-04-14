package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.R
import com.example.itogovoe.databinding.FragmentFilterBinding
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory

class FilterFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentFilterBinding
    private val adapter = FilterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getFilterItems()
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.filterItems.observe(viewLifecycleOwner) { filterList -> adapter.setData(filterList) }

        if (viewModel.filterState.value == null) {
            viewModel.initFilterState(
                FilterState(
                    allTimeColorBg = false,
                    monthColorBg = false,
                    weekColorBg = false,
                    selectedCurrencies = mutableListOf(),
                )
            )
        }

        binding.filterAllTime.setOnClickListener { viewModel.selectFilter(binding.filterAllTime.text.toString()) }
        binding.filterMonth.setOnClickListener { viewModel.selectFilter(binding.filterMonth.text.toString()) }
        binding.filterWeek.setOnClickListener { viewModel.selectFilter(binding.filterWeek.text.toString()) }
        viewModel.filterState.observe(viewLifecycleOwner) { renderState(it) }

        return binding.root
    }

    private fun renderState(state: FilterState) = with(binding) {
        if (state.allTimeColorBg) filterAllTime.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterAllTime.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.monthColorBg) filterMonth.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterMonth.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.weekColorBg) filterWeek.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterWeek.setBackgroundResource(R.drawable.round_bg_filter)
    }
}