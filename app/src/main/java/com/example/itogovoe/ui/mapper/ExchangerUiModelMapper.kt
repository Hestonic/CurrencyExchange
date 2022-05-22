package com.example.itogovoe.ui.mapper

import com.example.itogovoe.ui.fragments.exchange.ExchangerFragmentArgs
import com.example.itogovoe.ui.model.ExchangerUiModel

object ExchangerUiModelMapper {
    fun mapExchangeUiModel(args: ExchangerFragmentArgs, coefficient: Float): ExchangerUiModel =
        ExchangerUiModel(
            currencyNameParent = args.currencyParentName,
            currencyValueParent = 1f,
            currencyNameChild = args.currencyChildName,
            currencyValueChild = coefficient,
        )
}