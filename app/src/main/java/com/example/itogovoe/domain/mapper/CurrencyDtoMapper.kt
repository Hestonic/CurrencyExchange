package com.example.itogovoe.domain.mapper

import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity
import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.domain.model.Currency
import com.example.itogovoe.domain.model.HistoryDomainModel
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId

object CurrencyDtoMapper {

    fun mapResponseToDomainModel(response: Response<CurrencyResponse>): Currencies {
        val localDateTime = response.body()?.timestamp?.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }

        val rates: MutableList<Currency> = mutableListOf()
        response.body()?.rates?.let {
            for ((name, value) in it) rates.add(Currency(name, value))
        }

        response.body()?.base?.let {
            return Currencies(localDateTime, it, rates)
        }

        return Currencies(null, null, null)
    }

    fun mapDomainModelToCurrenciesEntity(currencies: Currencies): List<CurrenciesEntity>? {
        return currencies.rates?.map { CurrenciesEntity(0, it.name, it.value) }
    }

    // TODO: Избавиться от !!
    fun mapDomainModelToInfoEntity(currencies: Currencies): InfoEntity {
        return InfoEntity(0, currencies.date!!, currencies.base!!)
    }

    fun mapHistoryEntityToDomainModel(history: List<HistoryEntity>): List<HistoryDomainModel> {
        return history.map {
            HistoryDomainModel(
                date = it.date,
                currencyNameParent = it.currencyNameParent,
                currencyValueParent = it.currencyValueParent,
                currencyNameChild = it.currencyNameChild,
                currencyValueChild = it.currencyValueChild,
            )
        }
    }

    fun mapCurrenciesEntityToDomainModel(
        currenciesEntity: List<CurrenciesEntity>?,
        infoEntity: InfoEntity
    ): Currencies {
        return Currencies(
            date = infoEntity.lastUploadDate,
            base = infoEntity.base,
            rates = currenciesEntity?.map { Currency(it.name, it.value) }
        )
    }
}