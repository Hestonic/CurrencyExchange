package com.example.itogovoe.ui.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itogovoe.data.api.RetrofitInstance
import com.example.itogovoe.databinding.FragmentHomeBinding
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory

class HomeFragment : Fragment() {

    /*private val gridLayoutManager: GridLayoutManager =
        GridLayoutManager(requireContext(), 2)*/

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recyclerview Adapter
        val adapter = CurrencyAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
            //LinearLayoutManager(requireContext())

        // Получаем данные и передаём в адаптер
        val viewModelFactory = MainViewModelFactory(RetrofitInstance.repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getCurrency()
        viewModel.itemsLiveData.observe(viewLifecycleOwner) { response ->
            adapter.currencyList = response
            Log.d("MY_TAG", response.toString())
        }
    }
}