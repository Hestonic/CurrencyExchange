package com.example.itogovoe.ui.fragments.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.example.itogovoe.ui.main.TimeFilter
import java.util.*

class FilterFragment : Fragment() {

    private lateinit var viewModel: FilterViewModel
    private lateinit var binding: FragmentFilterBinding
    private val adapter = FilterAdapter()

    private var year = 0
    private var month = 0
    private var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel.getFilterItems()
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        setupRecycler()
        getDateCalendar()

        viewModel.filterLiveData.observe(viewLifecycleOwner) { filterUiModel ->
            adapter.setData(filterUiModel.currencyChips)

            with(filterUiModel) {
                with(binding) {
                    if (timeRange.isChecked) {

                    }

                    if (timeFilters.name == "За всё время" && timeFilters.isChecked)
                        filterAllTime.setBackgroundResource(R.drawable.round_bg_filter_selected)
                    else
                        filterAllTime.setBackgroundResource(R.drawable.round_bg_filter)

                    if (timeFilters.name == "За месяц" && timeFilters.isChecked)
                        filterMonth.setBackgroundResource(R.drawable.round_bg_filter_selected)
                    else
                        filterMonth.setBackgroundResource(R.drawable.round_bg_filter)

                    if (timeFilters.name == "За неделю" && timeFilters.isChecked)
                        filterWeek.setBackgroundResource(R.drawable.round_bg_filter_selected)
                    else
                        filterWeek.setBackgroundResource(R.drawable.round_bg_filter)
                }
            }
        }

        binding.filterAllTime.setOnClickListener {
            FilterInstance.timeFilter = TimeFilter.AllTime
            viewModel.getFilterItems()
            Log.d("filter_tag", "AllTime ${FilterInstance.timeFilter}")
        }

        binding.filterMonth.setOnClickListener {
            FilterInstance.timeFilter = TimeFilter.Month
            viewModel.getFilterItems()
            Log.d("filter_tag", "Month ${FilterInstance.timeFilter}")
        }

        binding.filterWeek.setOnClickListener {
            FilterInstance.timeFilter = TimeFilter.Week
            viewModel.getFilterItems()
            Log.d("filter_tag", "Week ${FilterInstance.timeFilter}")
        }

        binding.chooseDateFrom.setOnClickListener { dateFromChooser() }

        binding.chooseDateTo.setOnClickListener { dateToChooser() }

        /*// TODO: передача фильтра в HistoryFragment
        binding.filterButton.setOnClickListener {
            findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToHistoryFragment())
        }*/

        return binding.root
    }

    private fun dateFromChooser() {
        /*DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
            binding.chooseDateFrom.text = "$mYear-$mMonth-$mDay"
            year = mYear
            month = mMonth
            day = mDay
            // TODO: Filter
            renderState(FilterInstance)
        }, year, month, day).show()*/
    }

    private fun dateToChooser() {
        /*DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
                binding.chooseDateTo.text = "$mYear-$mMonth-$mDay"
                year = mYear
                month = mMonth
                day = mDay
                FilterInstance.dateTo = LocalDateTime.of(year, month + 1, day, 23, 50, 59)
                renderState(FilterInstance)
            }, year, month, day).show()*/
    }

    private fun getDateCalendar() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = FilterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FilterViewModel::class.java]
    }

    private fun setupRecycler() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    // TODO: восстановление состояния
    /*@SuppressLint("SetTextI18n")
    private fun dateChooser() {

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