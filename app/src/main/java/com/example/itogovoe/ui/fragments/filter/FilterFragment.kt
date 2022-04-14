package com.example.itogovoe.ui.fragments.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.App
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.filterItems.observe(viewLifecycleOwner) { filterList ->
            adapter.setData(filterList)
        }

       /* adapter.currencyFilterList.add("EUR")
        adapter.currencyFilterList.add("ZXC")
        adapter.currencyFilterList.add("ASF")
        adapter.currencyFilterList.add("GDW")
        adapter.currencyFilterList.add("DAS")
        adapter.currencyFilterList.add("GBC")
        adapter.currencyFilterList.add("NBV")
        adapter.currencyFilterList.add("KJH")
        adapter.currencyFilterList.add("NMK")
        adapter.currencyFilterList.add("TRD")
        adapter.currencyFilterList.add("JHG")*/
        return binding.root
    }
}