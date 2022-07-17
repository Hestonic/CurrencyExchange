package com.example.exchanger.ui.fragments.currency

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exchanger.App
import com.example.exchanger.R
import com.example.exchanger.databinding.FragmentHomeBinding
import com.example.exchanger.ui.fragments.currency.CurrencyViewModel.ExchangeNavArgs
import com.example.exchanger.ui.model.CurrencyUiModel

class CurrencyFragment : Fragment(), CurrencyPassClick, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: CurrencyViewModel
    private val adapter = CurrencyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel.initUiModel()
        viewModel.getCurrencies()
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOptionsMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecycler()

        viewModel.currenciesLiveData.observe(viewLifecycleOwner) { currenciesUiModel ->
            adapter.setData(currenciesUiModel.currencies)
            updateProgressBarVisible(currenciesUiModel.isLoading)
            updateErrorVisible(currenciesUiModel.isError)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { exchangeNavArgs ->
                navigateToExchangeFragment(exchangeNavArgs)
            }
        }

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
    
    private fun updateProgressBarVisible(isLoading: Boolean) {
        binding.progressCircular.isVisible = isLoading
        binding.recyclerview.isVisible = !isLoading
    }
    
    private fun updateErrorVisible(isError: Boolean) {
        binding.error.isVisible = isError
        binding.recyclerview.isVisible  = !isError
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
    
    private fun setupOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                activity?.menuInflater?.inflate(R.menu.currency_menu, menu)
                val search = menu.findItem(R.id.menu_search)
                val searchView = search?.actionView as? SearchView
                searchView?.setOnQueryTextListener(this@CurrencyFragment)
            }
        
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_search -> true
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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