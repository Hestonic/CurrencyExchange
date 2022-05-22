package com.example.itogovoe.domain.mapper

import android.util.Log
import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.domain.model.CurrencyDtoModel
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

// TODO: domain ничего не должен знать о других внешних слоях
object CurrencyDtoMapper {

    fun mapCurrencyResponseToCurrencyDomainModelList(response: Response<CurrencyResponse>): List<CurrencyDtoModel> {
        return if (response.isSuccessful) {
            response.body()!!.rates.map {
                CurrencyDtoModel(
                    lastUsedAt = LocalDateTime.now(),
                    updatedAt = timestampToLocalDateTime(response.body()!!.timestamp),
                    name = it.key,
                    value = it.value,
                    isFavourite = false
                )
            }
        } else {
            Log.d("CurrencyDtoMapper_response_error", response.errorBody().toString())
            emptyList()
        }
    }

    fun mapCurrencyDtoModelListToCurrenciesEntityList(currencies: List<CurrencyDtoModel>?): List<CurrenciesEntity>? {
        return currencies?.map {
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

    fun mapListCurrenciesEntityToDomainModelList(currenciesEntity: List<CurrenciesEntity>): List<CurrencyDtoModel> {
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

    fun mapCurrencyEntityToDomainModel(currencyEntity: CurrenciesEntity): CurrencyDtoModel {
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