package com.example.itogovoe.ui.mapper

import com.example.itogovoe.ui.fragments.exchange.ExchangerFragmentArgs
import com.example.itogovoe.ui.model.ExchangerUiModel

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
}