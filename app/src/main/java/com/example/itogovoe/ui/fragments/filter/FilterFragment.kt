package com.example.itogovoe.ui.fragments.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itogovoe.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recyclerview Adapter
        val adapter = FilterAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // TODO: данные должны браться из RoomDB таблицы History (currencyNameParent и currencyNameChild)
        adapter.currencyFilterList.add("EUR")
        adapter.currencyFilterList.add("ZXC")
        adapter.currencyFilterList.add("ASF")
        adapter.currencyFilterList.add("GDW")
        adapter.currencyFilterList.add("DAS")
        adapter.currencyFilterList.add("GBC")
        adapter.currencyFilterList.add("NBV")
        adapter.currencyFilterList.add("KJH")
        adapter.currencyFilterList.add("NMK")
        adapter.currencyFilterList.add("TRD")
        adapter.currencyFilterList.add("JHG")
    }

    companion object {
        // fun newInstance(param1: String, param2: String) = FilterFragment()
    }
}