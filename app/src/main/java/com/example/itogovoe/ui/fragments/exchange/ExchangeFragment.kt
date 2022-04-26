package com.example.itogovoe.ui.fragments.exchange

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.itogovoe.App
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.databinding.FragmentExchangeBinding
import com.example.itogovoe.ui.fragments.currency.CurrencyViewModel
import com.example.itogovoe.ui.fragments.currency.CurrencyViewModelFactory
import java.time.LocalDateTime

class ExchangeFragment : Fragment() {

    private lateinit var viewModel: ExchangeViewModel
    private lateinit var binding: FragmentExchangeBinding
    private val args by navArgs<ExchangeFragmentArgs>()
    private var coefficient: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()

        coefficient = calculateCrossCoefficient(
            args.currencyParentValue,
            args.currencyChildValue,
            args.currencyParentName
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        binding.currencyTextChild.text = args.currencyChildName
        binding.currencyTextParent.text = args.currencyParentName

        binding.currencyValueParent.setText("1.0")
        binding.currencyValueChild.text = coefficient.toString()

        viewModel.itemsLiveData.observe(viewLifecycleOwner) { currencyList ->
            var valueParent = 0f
            var valueChild = 0f
            currencyList.forEach {
                if (it.name == args.currencyParentName) {
                    valueParent = it.value.toFloat()
                }
                if (it.name == args.currencyChildName) {
                    valueChild = it.value.toFloat()
                }
            }
            coefficient = calculateCrossCoefficient(
                valueParent,
                valueChild,
                args.currencyParentName
            )

            val currentParentValue = binding.currencyValueParent.text.toString().toFloat()
            binding.currencyValueChild.text = (currentParentValue * coefficient).toString()
        }

        binding.currencyValueParent.addTextChangedListener {
            viewModel.isFresh().observe(viewLifecycleOwner) { isFresh ->
                if (isFresh) {
                    try {
                        val valueParent = binding.currencyValueParent.text.toString().toFloat()
                        val valueChild = valueParent * coefficient
                        binding.currencyValueChild.text = valueChild.toString()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Обновить данные")
                        .setMessage("Данные устарели, поэтому перед операцией их необходимо обновить")
                        .setPositiveButton("Хорошо") { _, _ ->
                            viewModel.getCurrency()
                            makeToast("Данные обновлены")
                        }.create().show()
                }
            }
        }

        binding.exchangeButton.setOnClickListener {
            viewModel.isFresh().observe(viewLifecycleOwner) { isFresh ->
                try {
                    when {
                        binding.currencyValueParent.text.isEmpty() -> {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Вы ввели пустое значение")
                                .setMessage("Введите значение в поле ввода, перед тем как осуществить транзакцию")
                                .setPositiveButton("Хорошо") { _, _ -> }
                                .create().show()
                        }
                        isFresh -> {
                            val historyEntity = HistoryEntity(
                                id = 0,
                                currencyNameChild = binding.currencyTextChild.text.toString(),
                                currencyNameParent = binding.currencyTextParent.text.toString(),
                                currencyValueChild = binding.currencyValueChild.text.toString()
                                    .toDouble(),
                                currencyValueParent = binding.currencyValueParent.text.toString()
                                    .toDouble(),
                                date = LocalDateTime.now()
                            )
                            viewModel.addHistoryItem(historyEntity)
                            makeToast("Транзакция добавлена в историю")
                        }
                        else -> {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Обновить данные")
                                .setMessage("Данные устарели, поэтому перед обменом их необходимо обновить")
                                .setPositiveButton("Хорошо") { _, _ ->
                                    viewModel.getCurrency()
                                    makeToast("Данные обновлены. Если вы согласны с курсом, нажмите на кнопку ещё раз")
                                }.create().show()
                        }
                    }
                } catch (e: Exception) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Вы ввели недопустимое значение")
                        .setMessage("Введите корректное значение в поле ввода, перед тем как осуществить транзакцию")
                        .setPositiveButton("Хорошо") { _, _ -> }
                        .create().show()
                }


            }
        }
        return binding.root
    }

    private fun makeToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun calculateCrossCoefficient(
        valueParent: Float,
        valueChild: Float,
        nameParent: String
    ): Float {
        return if (nameParent == "EUR") valueChild else valueChild / valueParent
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as App).dependencyInjection.repository
        val viewModelFactory = ExchangeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ExchangeViewModel::class.java]
    }
}