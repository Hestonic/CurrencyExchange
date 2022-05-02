package com.example.itogovoe.ui.fragments.currency

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.R
import com.example.itogovoe.databinding.FragmentHomeBinding
import com.example.itogovoe.ui.model.CurrencyUiModel

class CurrencyFragment : Fragment(), CurrencyPassClick, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: CurrencyViewModel
    private val adapter = CurrencyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initViewModel()
        viewModel.getCurrencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecycler()
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { adapter.setData(it) }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { binding.error.isGone = !it }
        return binding.root
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.currency_menu, menu)
        val item = menu.findItem(R.id.menu_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)

        /*activity?.menuInflater?.inflate(R.menu.currency_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)*/
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) searchCurrenciesDatabase(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) searchCurrenciesDatabase(query)
        return true
    }

    private fun searchCurrenciesDatabase(query: String) {
        val searchQuery = "%$query%"
        Log.d("asd", "searchQuery")
        viewModel.searchCurrenciesDatabase(searchQuery)
    }
}