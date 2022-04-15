package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.R
import com.example.itogovoe.databinding.FragmentFilterBinding
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory
import java.time.LocalDateTime

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
        renderState(FilterInstance)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.filterItems.observe(viewLifecycleOwner) { filterList -> adapter.setData(filterList) }

        binding.filterAllTime.setOnClickListener {
            FilterInstance.allTimeColorBg = true
            FilterInstance.monthColorBg = false
            FilterInstance.weekColorBg = false
            FilterInstance.dateTo = null
            FilterInstance.dateFrom = null
            renderState(FilterInstance)
        }

        binding.filterMonth.setOnClickListener {
            FilterInstance.allTimeColorBg = false
            FilterInstance.monthColorBg = true
            FilterInstance.weekColorBg = false
            FilterInstance.dateTo = LocalDateTime.now()
            FilterInstance.dateFrom = LocalDateTime.now().minusMonths(1)
            renderState(FilterInstance)
        }

        binding.filterWeek.setOnClickListener {
            FilterInstance.allTimeColorBg = false
            FilterInstance.monthColorBg = false
            FilterInstance.weekColorBg = true
            FilterInstance.dateTo = LocalDateTime.now()
            FilterInstance.dateFrom = LocalDateTime.now().minusDays(7)
            renderState(FilterInstance)
        }

        return binding.root
    }

    private fun renderState(state: FilterInstance) = with(binding) {
        if (state.allTimeColorBg) filterAllTime.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterAllTime.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.monthColorBg) filterMonth.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterMonth.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.weekColorBg) filterWeek.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterWeek.setBackgroundResource(R.drawable.round_bg_filter)
    }


}

// TODO: не забыть удалить
/*binding.filterAllTime.setOnClickListener { viewModel.selectFilter(binding.filterAllTime.text.toString()) }
        binding.filterMonth.setOnClickListener { viewModel.selectFilter(binding.filterMonth.text.toString()) }
        binding.filterWeek.setOnClickListener { viewModel.selectFilter(binding.filterWeek.text.toString()) }
        viewModel.filterState.observe(viewLifecycleOwner) { renderState(it) }*/


/*private fun renderState(state: FilterState) = with(binding) {
        if (state.allTimeColorBg) filterAllTime.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterAllTime.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.monthColorBg) filterMonth.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterMonth.setBackgroundResource(R.drawable.round_bg_filter)

        if (state.weekColorBg) filterWeek.setBackgroundResource(R.drawable.round_bg_filter_selected)
        else filterWeek.setBackgroundResource(R.drawable.round_bg_filter)
    }*/