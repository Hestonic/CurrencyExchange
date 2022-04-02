package com.example.itogovoe.ui.fragments.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.itogovoe.databinding.FragmentExchangeBinding

class ExchangeFragment : Fragment() {

    private lateinit var binding: FragmentExchangeBinding
    private val args by navArgs<ExchangeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: сделать подсчёт валюты и выводить правильные значения
        binding.currencyTextChild.text = args.currencyName
        binding.currencyTextParent.text = args.currencyName

        binding.currencyValueParent.setText("1")
        binding.currencyValueChild.setText(args.currencyValue.toString())
    }
}