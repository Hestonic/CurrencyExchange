package com.example.itogovoe.ui.fragments.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itogovoe.App
import com.example.itogovoe.databinding.FragmentHomeBinding

class CurrencyFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: CurrencyViewModel
    private val adapter = CurrencyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = CurrencyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]
        viewModel.getCurrency()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
        (binding.recyclerview.layoutManager as GridLayoutManager).scrollToPosition(0)
        binding.recyclerview.hasFixedSize()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { response ->
            adapter.currencyList = response
            adapter.notifyDataSetChanged()
            Log.d("MY_TAG", response.toString())
        }
    }
}