package com.example.itogovoe.domain.mapper

import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.data.api.CurrencyResponse
import com.example.itogovoe.domain.model.Currency
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId

/*
Маппер преобразовывает данные, которые ему возвращает api в Response<Currency> в
те данные, что нам нужны в Repository (данные типа data class Currencies)

Т.е. берём поле из Currency и сетим в новый объект Currencies
*/

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
}