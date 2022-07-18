package com.example.exchanger.domain.mapper

import com.example.exchanger.data.api.CurrencyResponse
import com.example.exchanger.data.sources.local_source.entities.CurrenciesEntity
import com.example.exchanger.domain.model.CurrencyDtoModel
import retrofit2.Response

interface CurrencyDtoMapper {
    fun mapCurrencyResponseToCurrencyDomainModelList(response: Response<CurrencyResponse>): List<CurrencyDtoModel>
    fun mapCurrencyDtoModelListToCurrenciesEntityList(currencies: List<CurrencyDtoModel>): List<CurrenciesEntity>
    fun mapListCurrenciesEntityToDomainModelList(currenciesEntity: List<CurrenciesEntity>): List<CurrencyDtoModel>
    fun mapCurrencyEntityToDomainModel(currencyEntity: CurrenciesEntity): CurrencyDtoModel
}