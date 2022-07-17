package com.example.itogovoe.domain.mapper

import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.domain.model.CurrencyDtoModel
import retrofit2.Response

interface CurrencyDtoMapper {
    fun mapCurrencyResponseToCurrencyDomainModelList(response: Response<CurrencyResponse>): List<CurrencyDtoModel>
    fun mapCurrencyDtoModelListToCurrenciesEntityList(currencies: List<CurrencyDtoModel>): List<CurrenciesEntity>
    fun mapListCurrenciesEntityToDomainModelList(currenciesEntity: List<CurrenciesEntity>): List<CurrencyDtoModel>
    fun mapCurrencyEntityToDomainModel(currencyEntity: CurrenciesEntity): CurrencyDtoModel
}