package com.example.exchanger.data.mapper

import com.example.exchanger.data.api.CurrencyResponse
import com.example.exchanger.data.sources.local_source.entities.CurrenciesEntity
import com.example.exchanger.domain.mapper.CurrencyDtoMapper
import com.example.exchanger.domain.model.CurrencyDtoModel
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object CurrencyDtoMapperImpl : CurrencyDtoMapper {

    override fun mapCurrencyResponseToCurrencyDomainModelList(response: Response<CurrencyResponse>): List<CurrencyDtoModel> {
        var currencyDtoList: List<CurrencyDtoModel> = emptyList()
        if (response.isSuccessful) {
            response.body()?.let { currencyResponse ->
                currencyDtoList = currencyResponse.rates.map { currency ->
                    CurrencyDtoModel(
                        lastUsedAt = LocalDateTime.now(),
                        updatedAt = timestampToLocalDateTime(currencyResponse.timestamp),
                        name = currency.key,
                        value = currency.value,
                        isFavourite = false
                    )
                }
            }
        }
        return currencyDtoList
    }

    override fun mapCurrencyDtoModelListToCurrenciesEntityList(currencies: List<CurrencyDtoModel>): List<CurrenciesEntity> {
        return currencies.map {
            CurrenciesEntity(
                name = it.name,
                value = it.value,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                lastUsedAt = LocalDateTime.now(),
                isFavourite = false,
            )
        }
    }

    override fun mapListCurrenciesEntityToDomainModelList(currenciesEntity: List<CurrenciesEntity>): List<CurrencyDtoModel> {
        return currenciesEntity.map {
            CurrencyDtoModel(
                lastUsedAt = it.lastUsedAt,
                updatedAt = it.updatedAt,
                name = it.name,
                value = it.value,
                isFavourite = it.isFavourite,
            )
        }
    }

    override fun mapCurrencyEntityToDomainModel(currencyEntity: CurrenciesEntity): CurrencyDtoModel {
        return CurrencyDtoModel(
            isFavourite = currencyEntity.isFavourite,
            lastUsedAt = currencyEntity.lastUsedAt,
            updatedAt = currencyEntity.updatedAt,
            name = currencyEntity.name,
            value = currencyEntity.value
        )
    }

    private fun timestampToLocalDateTime(timestamp: Long): LocalDateTime {
        return timestamp.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }

    }
}