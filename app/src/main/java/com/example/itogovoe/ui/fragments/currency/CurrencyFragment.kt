package com.example.itogovoe.ui.fragments.currency

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.R
import com.example.itogovoe.databinding.FragmentHomeBinding
import com.example.itogovoe.ui.model.CurrencyUiModel
import com.example.itogovoe.ui.fragments.currency.CurrencyViewModel.ExchangeNavArgs

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
        setProgressBarVisible()

        viewModel.currenciesLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
            setProgressBarGone()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { exchangeNavArgs ->
                navigateToExchangeFragment(exchangeNavArgs)
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { binding.error.isGone = !it }
        return binding.root
    }

    private fun navigateToExchangeFragment(exchangeNavArgs: ExchangeNavArgs) {
        val action = CurrencyFragmentDirections.actionHomeFragmentToExchangeFragment(
            currencyParentName = exchangeNavArgs.currencyParentName,
            currencyChildName = exchangeNavArgs.currencyChildName,
        )
        findNavController().navigate(action)
    }

    override fun passIsFavouriteClick(currencyName: String, isFavourite: Boolean) {
        viewModel.updateCurrencyIsFavourite(currencyName, isFavourite)
    }

    override fun passClick(currencyChildName: String) {
        viewModel.handleSingleClick(currencyChildName)
    }

    override fun passLongClick(currencyUiModel: CurrencyUiModel) {
        viewModel.handleLongClick(currencyUiModel)
    }

    private fun setProgressBarVisible() {
        binding.progressCircular.isGone = false
        binding.recyclerview.isGone = true
    }

    private fun setProgressBarGone() {
        binding.progressCircular.isGone = true
        binding.recyclerview.isGone = false
    }

    private fun initViewModel() {
        val currencyRepository =
            (requireActivity().application as App).dependencyInjection.currencyRepositoryImpl
        val viewModelFactory = CurrencyViewModelFactory(currencyRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]
    }

    private fun setupRecycler() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
        (binding.recyclerview.layoutManager as GridLayoutManager).scrollToPosition(0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.currency_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
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
        viewModel.searchCurrencies(searchQuery)
    }
}