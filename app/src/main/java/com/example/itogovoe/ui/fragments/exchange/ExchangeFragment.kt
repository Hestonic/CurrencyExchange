package com.example.itogovoe.ui.fragments.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.itogovoe.databinding.FragmentExchangeBinding

class ExchangeFragment : Fragment() {

    private lateinit var binding: FragmentExchangeBinding
    private val args by navArgs<ExchangeFragmentArgs>()
    private var coefficient: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val valueParent = args.currencyParentValue
        val valueChild = args.currencyChildValue
        val nameParent = args.currencyParentName
        val base = args.base
        coefficient = if (nameParent == base) valueChild else valueChild / valueParent

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        binding.currencyTextChild.text = args.currencyChildName
        binding.currencyTextParent.text = args.currencyParentName

        binding.currencyValueParent.setText("1.0")
        val startValueChild = args.currencyParentValue * coefficient
        binding.currencyValueChild.text = startValueChild.toString()

        binding.currencyValueParent.addTextChangedListener {
            try {
                val valueParent = binding.currencyValueParent.text.toString().toFloat()
                val valueChild = valueParent * coefficient
                binding.currencyValueChild.text = valueChild.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return binding.root
    }
}