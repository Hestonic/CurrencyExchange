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
        binding.currencyTextChild.text = args.currencyChildName
        binding.currencyTextParent.text = args.currencyParentName

        binding.currencyValueParent.setText(args.currencyParentValue.toString())
        binding.currencyValueChild.setText(args.currencyChildValue.toString())
        return binding.root
    }
}