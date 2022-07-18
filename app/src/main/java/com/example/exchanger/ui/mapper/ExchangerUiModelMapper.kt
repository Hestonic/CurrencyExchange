package com.example.exchanger.ui.mapper

import com.example.exchanger.ui.fragments.exchange.ExchangerFragmentArgs
import com.example.exchanger.ui.model.ExchangerUiModel

object ExchangerUiModelMapper {
    fun mapLoadingExchangeUiModel(): ExchangerUiModel =
        ExchangerUiModel(
            isLoading = true,
            currencyNameParent = "",
            currencyValueParent = 0f,
            currencyNameChild = "",
            currencyValueChild = 0f,
        )
    
    fun mapExchangeUiModel(args: ExchangerFragmentArgs, coefficient: Float): ExchangerUiModel =
        ExchangerUiModel(
            isLoading = false,
            currencyNameParent = args.currencyParentName,
            currencyValueParent = 1f,
            currencyNameChild = args.currencyChildName,
            currencyValueChild = coefficient,
        )
    
    fun reverseCurrencyUiModel(oldExchangerUiModel: ExchangerUiModel) =
        oldExchangerUiModel.copy(
            currencyNameParent = oldExchangerUiModel.currencyNameChild,
            currencyNameChild = oldExchangerUiModel.currencyNameParent,
            currencyValueParent = oldExchangerUiModel.currencyValueChild,
            currencyValueChild = oldExchangerUiModel.currencyValueParent
        )
    
    fun mapUpdateUiModel(oldExchangerUiModel: ExchangerUiModel, coefficient: Float) =
        oldExchangerUiModel.copy(
            currencyValueParent = oldExchangerUiModel.currencyValueParent,
            currencyValueChild = oldExchangerUiModel.currencyValueParent * coefficient
        )
    
    fun refreshUiModel(
        oldExchangerUiModel: ExchangerUiModel,
        valueParent: Float,
        coefficient: Float
    ) = oldExchangerUiModel.copy(
            currencyValueParent = valueParent,
            currencyValueChild = valueParent * coefficient
        )
}