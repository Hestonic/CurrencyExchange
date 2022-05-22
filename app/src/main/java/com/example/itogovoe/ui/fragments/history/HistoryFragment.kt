package com.example.itogovoe.ui.fragments.history

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
    
    // TODO: попробовать сделать через адаптер делегат
    private val historyAdapter = HistoryAdapter()
    private val historyChipsAdapter = HistoryChipsAdapter()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        setHasOptionsMenu(true)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupRecyclerHistory()
        setupRecyclerHistoryChips()
        viewModel.allHistory.observe(viewLifecycleOwner) { history ->
            historyAdapter.setData(history)
        }
        viewModel.historyChips.observe(viewLifecycleOwner) { chips ->
            historyChipsAdapter.setData(chips)
        }
        FilterInstance.timeFilter.observe(viewLifecycleOwner) { timeFilter ->
            viewModel.getData(timeFilter)
        }
        
        return binding.root
    }
    
    private fun setupRecyclerHistory() {
        binding.recyclerviewHistory.adapter = historyAdapter
        binding.recyclerviewHistory.layoutManager = LinearLayoutManager(requireContext())
    }
    
    private fun setupRecyclerHistoryChips() {
        binding.recyclerviewChips.adapter = historyChipsAdapter
        binding.recyclerviewChips.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
    
    private fun initViewModel() {
        val historyRepository =
            (requireActivity().application as App).dependencyInjection.historyRepositoryImpl
        val viewModelFactory = HistoryViewModelFactory(historyRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]
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