package com.example.itogovoe.ui.fragments.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.itogovoe.App
import com.example.itogovoe.data.source.local_source.entities.HistoryEntity
import com.example.itogovoe.databinding.FragmentExchangeBinding
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory
import java.time.LocalDateTime

class ExchangeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentExchangeBinding
    private val args by navArgs<ExchangeFragmentArgs>()
    private var coefficient: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

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

        binding.exchangeButton.setOnClickListener {
            val historyEntity = HistoryEntity(
                id = 0,
                currencyNameChild = binding.currencyTextChild.text.toString(),
                currencyNameParent = binding.currencyTextParent.text.toString(),
                currencyValueChild = binding.currencyValueChild.text.toString().toDouble(),
                currencyValueParent = binding.currencyValueParent.text.toString().toDouble(),
                date = LocalDateTime.now()
            )
            viewModel.addHistoryItem(historyEntity)
        }

        return binding.root
    }
}