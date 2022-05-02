package com.example.itogovoe.ui.fragments.exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itogovoe.domain.repository.Repository
import com.example.itogovoe.ui.mapper.HistoryUiModelMapper
import com.example.itogovoe.ui.model.ExchangerUiModel
import com.example.itogovoe.ui.model.HistoryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangerViewModel(private val repository: Repository) : ViewModel() {

    private val _exchanger: MutableLiveData<ExchangerUiModel> = MutableLiveData()
    val exchanger: LiveData<ExchangerUiModel> = _exchanger

    private val _isFreshOnHistorySave = MutableLiveData<Boolean>()
    val isFreshOnHistorySave: LiveData<Boolean> = _isFreshOnHistorySave

    private val _isFreshOnTextChange = MutableLiveData<Boolean>()
    val isFreshOnTextChange: LiveData<Boolean> = _isFreshOnTextChange

    private var coefficient = 0f

    fun initUiModel(args: ExchangerFragmentArgs) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrencies()
            val valueParent = repository.readCurrency(args.currencyParentName).value.toFloat()
            val valueChild = repository.readCurrency(args.currencyChildName).value.toFloat()
            coefficient = valueChild / valueParent
            val exchangerUiModel = ExchangerUiModel(
                currencyNameParent = args.currencyParentName,
                currencyValueParent = 1f,
                currencyNameChild = args.currencyChildName,
                currencyValueChild = coefficient,
            )
            Log.d("init_ui_model_tag", exchangerUiModel.toString())
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
            repository.getCurrencies()
            val oldExchangerUiModel = _exchanger.value
            val valueParentDb = repository.readCurrency(args.currencyParentName).value.toFloat()
            val valueChildDb = repository.readCurrency(args.currencyChildName).value.toFloat()
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

    fun addHistoryItem(historyUiModel: HistoryUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistoryItem(
                HistoryUiModelMapper.mapHistoryUiModelToDomainModel(historyUiModel)
            )
        }
    }

    fun checkIsFreshOnHistorySave() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnHistorySave.postValue(repository.isFresh())
        }
    }

    fun checkIsFreshOnTextChange() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFreshOnTextChange.postValue(repository.isFresh())
        }
    }

}