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
        val localDateTime = Instant.ofEpochSecond(response.body()?.timestamp!!.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val rates: MutableList<Currency> = mutableListOf()
        for ((name, value) in response.body()?.rates!!) rates.add(Currency(name, value))

        return Currencies(
            localDateTime, response.body()?.base!!, rates
        )
    }
}