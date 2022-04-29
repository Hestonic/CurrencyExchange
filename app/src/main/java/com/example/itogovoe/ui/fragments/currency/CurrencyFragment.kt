package com.example.itogovoe.ui.fragments.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.databinding.FragmentHomeBinding
import com.example.itogovoe.ui.model.CurrencyUiModel

class CurrencyFragment : Fragment(), CurrencyPassClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: CurrencyViewModel
    private val adapter = CurrencyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel.getCurrencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecycler()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { adapter.setData(it) }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { binding.error.isGone = !it }
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = CurrencyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]
    }

    private fun setupRecycler() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
        (binding.recyclerview.layoutManager as GridLayoutManager).scrollToPosition(0)
    }

    override fun passIsFavouriteClick(currencyName: String, isFavourite: Boolean) {
        viewModel.updateCurrencyIsFavourite(currencyName, isFavourite)
        viewModel.getCurrencies()
    }

    override fun passClick(
        currencyParentName: String,
        currencyChildName: String
    ) {
        viewModel.updateCurrencyLastUsedAt(currencyChildName)
        viewModel.updateCurrencyLastUsedAt(currencyParentName)
        viewModel.getCurrencies()
        val action = CurrencyFragmentDirections.actionHomeFragmentToExchangeFragment(
            currencyParentName = currencyParentName,
            currencyChildName = currencyChildName,
        )
        findNavController().navigate(action)
    }

    override fun passLongClick(currencyName: String) {
        viewModel.updateCurrencyLastUsedAt(currencyName)
        viewModel.getCurrencies()
    }

    override fun passIsCheckedLongClick(currencyUiModel: CurrencyUiModel) {
        viewModel.isCheckedSort(currencyUiModel)
    }
}