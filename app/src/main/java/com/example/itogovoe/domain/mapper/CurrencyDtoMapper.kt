package com.example.itogovoe.domain.mapper

import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.domain.model.CurrencyDtoModel
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object CurrencyDtoMapper {

    fun mapCurrencyResponseToCurrencyDomainModel(response: Response<CurrencyResponse>): List<CurrencyDtoModel>? {
        return response.body()?.rates?.map {
            CurrencyDtoModel(
                lastUsedAt = null,
                updatedAt = response.body()?.timestamp?.let { timestamp ->
                    Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                },
                name = it.key,
                value = it.value,
                isFavourite = null
            )
        }
    }

    fun mapCurrencyDtoModelToCurrenciesEntityList(currencies: List<CurrencyDtoModel>?): List<CurrenciesEntity>? {
        return currencies?.map {
            CurrenciesEntity(
                //  TODO: Избавитсья от !!
                name = it.name!!,
                value = it.value!!,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                lastUsedAt = LocalDateTime.now(),
                isFavourite = false,
            )
        }
    }

    // TODO: Избавиться от !!
    fun mapCurrenciesEntityToDomainModel(currenciesEntity: List<CurrenciesEntity>?): List<CurrencyDtoModel> {
        return currenciesEntity!!.map {
            CurrencyDtoModel(
                lastUsedAt = it.lastUsedAt,
                updatedAt = it.updatedAt,
                name = it.name,
                value = it.value,
                isFavourite = it.isFavourite,
            )
        }
    }
}