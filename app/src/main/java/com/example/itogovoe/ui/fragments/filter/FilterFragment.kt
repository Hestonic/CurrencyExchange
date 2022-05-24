package com.example.itogovoe.ui.fragments.filter

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.R
import com.example.itogovoe.databinding.FragmentFilterBinding
import com.example.itogovoe.ui.main.FilterInstance
import com.example.itogovoe.ui.model.TimeRangeUiModel
import java.util.*

class FilterFragment : Fragment(), FilterPassClick {

    private lateinit var viewModel: FilterViewModel
    private lateinit var binding: FragmentFilterBinding
    // TODO: попробовать сделать через один адаптер (адаптер делегат)
    private val currencyChipsAdapter = CurrencyChipsAdapter()
    private val timeChipsAdapter = TimeChipsAdapter(this)

    private var year = 0
    private var month = 0
    private var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        setupCurrencyChipsRecycler()
        setupTimeChipsRecycler()
        getDateCalendar()

        viewModel.filterLiveData.observe(viewLifecycleOwner) { filterUiModel ->
            currencyChipsAdapter.setData(filterUiModel.currencyChips)
            timeChipsAdapter.setData(filterUiModel.timeFilters)
            updateDateChoosers(filterUiModel.timeRange)
        }
        
        FilterInstance.timeFilter.observe(viewLifecycleOwner) { timeFilter ->
            viewModel.initFilterUiModel(timeFilter)
        }
        
        FilterInstance.currencyFilter.observe(viewLifecycleOwner) { currencyFilter ->
//            viewModel.updateCurrencyFilterChips(currencyFilter.allCurrenciesAsFilter)
        }

        binding.chooseDateFrom.setOnClickListener { dateFromChooser() }
        binding.chooseDateTo.setOnClickListener { dateToChooser() }

        return binding.root
    }

    override fun passChipsClick(name: String) {
        viewModel.updateTimeFilterChips(name)
        viewModel.setTimeFilterChipsInInstance(name)
    }
    
    private fun dateFromChooser() {
        DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
            val date = "$mDay.$mMonth.$mYear"
            binding.chooseDateFrom.text = date
            viewModel.dateFromChooser(mYear, mMonth, mDay)
        }, year, month, day).show()
    }

    private fun dateToChooser() {
        DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
            val date = "$mDay.$mMonth.$mYear"
            binding.chooseDateTo.text = date
            viewModel.dateToChooser(mYear, mMonth, mDay)
        }, year, month, day).show()
    }
    
    private fun updateDateChoosers(timeRangeUiModel: TimeRangeUiModel) {
        if (timeRangeUiModel.isChecked) {
            binding.chooseDateFrom.setBackgroundResource(R.drawable.round_bg_filter_selected)
            binding.chooseDateTo.setBackgroundResource(R.drawable.round_bg_filter_selected)
        } else {
            binding.chooseDateFrom.setBackgroundResource(R.drawable.round_bg_filter)
            binding.chooseDateTo.setBackgroundResource(R.drawable.round_bg_filter)
        }
        binding.chooseDateTo.text = timeRangeUiModel.dateTo
        binding.chooseDateFrom.text = timeRangeUiModel.dateFrom
    }

    private fun getDateCalendar() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun initViewModel() {
        val historyRepository = (requireActivity().application as App).dependencyInjection.historyRepositoryImpl
        val viewModelFactory = FilterViewModelFactory(historyRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FilterViewModel::class.java]
    }

    private fun setupCurrencyChipsRecycler() {
        binding.currencyChipsRecyclerview.adapter = currencyChipsAdapter
        binding.currencyChipsRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupTimeChipsRecycler() {
        binding.timeChipsRecyclerview.adapter = timeChipsAdapter
        binding.timeChipsRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.timeChipsRecyclerview.hasFixedSize()
    }
}