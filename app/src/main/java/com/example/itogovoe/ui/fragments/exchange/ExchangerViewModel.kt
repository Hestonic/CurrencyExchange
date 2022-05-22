package com.example.itogovoe.ui.fragments.exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.CurrencyRepository
import com.example.itogovoe.domain.repository.HistoryRepository
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import com.example.itogovoe.ui.model.ExchangerUiModel
import com.example.itogovoe.ui.model.HistoryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ExchangerViewModel(
    private val currencyRepository: CurrencyRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _exchanger: MutableLiveData<ExchangerUiModel> = MutableLiveData()
    val exchanger: LiveData<ExchangerUiModel> get() = _exchanger

    private val _isFreshOnHistorySave = MutableLiveData<Boolean>()
    val isFreshOnHistorySave: LiveData<Boolean> get() = _isFreshOnHistorySave

    private val _isFreshOnTextChange = MutableLiveData<Boolean>()
    val isFreshOnTextChange: LiveData<Boolean> get() = _isFreshOnTextChange

    private var coefficient = 0f

    fun initUiModel(args: ExchangerFragmentArgs) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.getCurrencies()
            val valueParent = currencyRepository.readCurrency(args.currencyParentName).value
            val valueChild = currencyRepository.readCurrency(args.currencyChildName).value
            coefficient = valueChild / valueParent
            val exchangerUiModel = ExchangerUiModel(
                currencyNameParent = args.currencyParentName,
                currencyValueParent = 1f,
                currencyNameChild = args.currencyChildName,
                currencyValueChild = coefficient,
            )
            _exchanger.postValue(exchangerUiModel)
        }
    }

    fun refreshUiModelValues(valueParent: Float) {
        viewModelScope.launch {
            val oldExchangerUiModel = _exchanger.value
            _exchanger.postValue(
                oldExchangerUiModel?.copy(
                    currencyValueParent = valueParent,
                    currencyValueChild = valueParent * coefficient
                )
            )
            Log.d("refresh_ui_model_values_tag", oldExchangerUiModel.toString())
        }
    }

    fun updateCurrencies(args: ExchangerFragmentArgs) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.getCurrencies()
            val oldExchangerUiModel = _exchanger.value
            val valueParentDb = currencyRepository.readCurrency(args.currencyParentName).value
            val valueChildDb = currencyRepository.readCurrency(args.currencyChildName).value
            coefficient = valueChildDb / valueParentDb
            _exchanger.postValue(
                oldExchangerUiModel?.copy(
                    currencyValueParent = oldExchangerUiModel.currencyValueParent,
                    currencyValueChild = oldExchangerUiModel.currencyValueParent * coefficient
                )
            )
            Log.d("update_currencies_tag", oldExchangerUiModel.toString())
        }
    }

    fun addHistoryItem(
        currencyNameChild: String,
        currencyNameParent: String,
        currencyValueChild: Float,
        currencyValueParent: Float
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyUiModel = HistoryUiModel(
                currencyNameChild = currencyNameChild,
                currencyNameParent = currencyNameParent,
                currencyValueChild = currencyValueChild,
                currencyValueParent = currencyValueParent,
                date = LocalDateTime.now().toString(),
            )
            historyRepository.addHistoryItem(
                HistoryUiModelMapper.mapHistoryUiModelToDomainModel(historyUiModel)
            )
        }
    }

    fun checkIsFreshOnHistorySave() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnHistorySave.postValue(currencyRepository.isFresh())
        }
    }

    fun checkIsFreshOnTextChange() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnTextChange.postValue(currencyRepository.isFresh())
        }
    }

}