package com.example.itogovoe.ui.fragments.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.R
import com.example.itogovoe.databinding.FragmentHistoryBinding
import com.example.itogovoe.ui.main.FilterInstance

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    private val adapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel.getHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        viewModel.historySearchItems.observe(viewLifecycleOwner) { it.let { adapter.setData(it) } }
        viewModel.historyItems.observe(viewLifecycleOwner) { history -> adapter.setData(history) }
        viewModel.getData()
        setHasOptionsMenu(true)
        setupRecycler()
        return binding.root
    }

    private fun setupRecycler() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = HistoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    private fun getData(state: FilterInstance) {
//        binding.filterAllTime.isGone = !state.allTimeFilter
//        binding.filterMonth.isGone = !state.monthFilter
//        binding.filterWeek.isGone = !state.weekFilter
//        binding.filterCurrencyName.isGone = state.selectedCurrencies.isEmpty()

//        if (state.rangeFilter) {
//            binding.filterTimeRange.isGone = !state.rangeFilter
//            val dateFrom = state.dateFrom?.toLocalDate()
//            val dateTo = state.dateTo.toLocalDate()
//            binding.filterTimeRange.text = "$dateFrom - $dateTo"
//        }
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
}