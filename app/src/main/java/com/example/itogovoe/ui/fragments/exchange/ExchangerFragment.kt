package com.example.itogovoe.ui.fragments.exchange

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.itogovoe.App
import com.example.itogovoe.databinding.FragmentExchangeBinding

class ExchangerFragment : Fragment() {

    private lateinit var viewModel: ExchangerViewModel
    private lateinit var binding: FragmentExchangeBinding
    private val args by navArgs<ExchangerFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel.initUiModel(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        setCurrenciesNames()
        setProgressBarVisible()

        viewModel.exchanger.observe(viewLifecycleOwner) { exchangerUiModel ->
            binding.currencyValueChild.text = exchangerUiModel.currencyValueChild.toString()
            setProgressBarGone()
        }
        viewModel.isFreshOnTextChange.observe(viewLifecycleOwner) { onValueParentTextChange(it) }
        viewModel.isFreshOnHistorySave.observe(viewLifecycleOwner) { onExchangeButtonClick(it) }

        binding.currencyValueParent.addTextChangedListener { viewModel.checkIsFreshOnTextChange() }
        binding.exchangeButton.setOnClickListener { viewModel.checkIsFreshOnHistorySave() }

        return binding.root
    }

    private fun setCurrenciesNames() {
        binding.currencyTextChild.text = args.currencyChildName
        binding.currencyTextParent.text = args.currencyParentName
        binding.currencyValueParent.setText("1.0")
    }

    private fun onExchangeButtonClick(dataIsFresh: Boolean) {
        try {
            when {
                binding.currencyValueParent.text.isEmpty() -> showAlertDialogEmptyValue()
                dataIsFresh -> addToHistory()
                else -> showAlertDialogNotFreshValues()
            }
        } catch (e: Exception) {
            showAlertDialogInvalidValue()
        }
    }

    private fun showAlertDialogNotFreshValues() {
        AlertDialog.Builder(requireContext())
            .setTitle("Обновить данные")
            .setMessage("Данные устарели, поэтому перед обменом их необходимо обновить")
            .setCancelable(false)
            .setPositiveButton("Хорошо") { _, _ ->
                setProgressBarVisible()
                viewModel.updateCurrencies(args)
                makeToast("Данные обновлены. Если вы согласны с курсом, сохраните в историю ещё раз")
            }.create().show()
    }

    private fun showAlertDialogEmptyValue() {
        AlertDialog.Builder(requireContext())
            .setTitle("Вы ввели пустое значение")
            .setMessage("Введите значение в поле ввода, перед тем как осуществить транзакцию")
            .setCancelable(false)
            .setPositiveButton("Хорошо") { _, _ -> }
            .create().show()
    }

    private fun showAlertDialogInvalidValue() {
        AlertDialog.Builder(requireContext())
            .setTitle("Вы ввели недопустимое значение")
            .setMessage("Введите корректное значение в поле ввода, перед тем как осуществить транзакцию")
            .setCancelable(false)
            .setPositiveButton("Хорошо") { _, _ -> }
            .create().show()
    }

    private fun onValueParentTextChange(dataIsFresh: Boolean) {
        if (dataIsFresh)
            try {
                viewModel.refreshUiModelValues(
                    binding.currencyValueParent.text.toString().toFloat()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        else showAlertDialogNotFreshValues()
    }

    private fun addToHistory() {
        viewModel.addHistoryItem(
            currencyNameChild = binding.currencyTextChild.text.toString(),
            currencyNameParent = binding.currencyTextParent.text.toString(),
            currencyValueChild = binding.currencyValueChild.text.toString().toFloat(),
            currencyValueParent = binding.currencyValueParent.text.toString().toFloat()
        )
        makeToast("Транзакция добавлена в историю")
    }

    private fun setProgressBarVisible() {
        binding.blockParent.isVisible = false
        binding.blockChild.isVisible = false
        binding.exchangeButton.isVisible = false
        binding.progressCircular.isVisible = true
    }

    private fun setProgressBarGone() {
        binding.blockParent.isVisible = true
        binding.blockChild.isVisible = true
        binding.exchangeButton.isVisible = true
        binding.progressCircular.isVisible = false
    }

    private fun makeToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        val currencyRepository = (requireActivity().application as App).dependencyInjection.currencyRepositoryImpl
        val historyRepository = (requireActivity().application as App).dependencyInjection.historyRepositoryImpl
        val viewModelFactory = ExchangerViewModelFactory(currencyRepository, historyRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ExchangerViewModel::class.java]
    }

}