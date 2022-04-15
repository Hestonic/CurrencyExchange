package com.example.itogovoe.ui.fragments.history

import android.os.Bundle
import android.view.*
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.R
import com.example.itogovoe.data.sources.local_source.converters.DateConverter
import com.example.itogovoe.databinding.FragmentHistoryBinding
import com.example.itogovoe.domain.mapper.CurrencyDtoMapper
import com.example.itogovoe.ui.fragments.filter.FilterInstance
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory
import com.example.itogovoe.ui.mapper.CurrencyUiModelMapper
import java.time.LocalDateTime

class HistoryFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentHistoryBinding
    private val adapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        renderState(FilterInstance)
        setHasOptionsMenu(true)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        viewModel.historyItems.observe(viewLifecycleOwner) { history -> adapter.setData(history) }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_filter) {
            val action = HistoryFragmentDirections.actionHistoryFragmentToFilterFragment()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderState(state: FilterInstance) {
        binding.filterAllTime.isGone = !state.allTimeColorBg
        binding.filterMonth.isGone = !state.monthColorBg
        binding.filterWeek.isGone = !state.weekColorBg
        if ((FilterInstance.dateTo != null) and (FilterInstance.dateFrom != null)) {
            searchDatabase(FilterInstance.dateTo!!, FilterInstance.dateFrom!!)
        }
    }

    private fun searchDatabase(dateTo: LocalDateTime, dateFrom: LocalDateTime) {
        val dateToTimestamp = DateConverter().localDateTimeToTimestamp(dateTo)
        val dateFromTimestamp = DateConverter().localDateTimeToTimestamp(dateFrom)
        viewModel.searchDateHistory(dateFromTimestamp, dateToTimestamp)
            .observe(viewLifecycleOwner) {
                it.let {
                    adapter.setData(
                        CurrencyUiModelMapper.mapHistoryEntityToUiModel(
                            CurrencyDtoMapper.mapHistoryEntityToDomainModel(it)
                        )
                    )
                }
            }
    }

}

// TODO: Не забыть удалить
/*viewModel.filterState.observe(viewLifecycleOwner) { filterState -> renderState(filterState)}

        if (viewModel.filterState.value == null) {
            viewModel.initFilterState(
                FilterState(
                    allTimeColorBg = true,
                    monthColorBg = false,
                    weekColorBg = false,
                    selectedCurrencies = mutableListOf(),
                    dateTo = null,
                    dateFrom = null,
                )
            )
        }*/

/**/