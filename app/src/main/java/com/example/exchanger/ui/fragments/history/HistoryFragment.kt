package com.example.exchanger.ui.fragments.history

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchanger.App
import com.example.exchanger.R
import com.example.exchanger.databinding.FragmentHistoryBinding
import com.example.exchanger.ui.main.FilterInstance

class HistoryFragment : Fragment() {
    
    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    
    // TODO: попробовать сделать через адаптер делегат
    private val historyAdapter = HistoryAdapter()
    private val historyChipsAdapter = HistoryChipsAdapter()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOptionsMenu()
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
        FilterInstance.filters.observe(viewLifecycleOwner) { filtersModel ->
            viewModel.getData(filtersModel.timeFilter)
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
    
    private fun setupOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.filter_menu, menu)
            }
            
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_filter -> {
                        val action = HistoryFragmentDirections.actionHistoryFragmentToFilterFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}