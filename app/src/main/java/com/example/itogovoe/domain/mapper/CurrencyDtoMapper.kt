package com.example.itogovoe.domain.mapper

import com.example.itogovoe.domain.model.Currencies
import com.example.itogovoe.data.api.Currency
import retrofit2.Response

/*
Маппер преобразовывает данные, которые ему возвращает api в Response<Currency> в
те данные, что нам нужны в Repository (данные типа data class Currencies)

Т.е. берём поле из Currency и сетим в новый объект Carrencies
*/

object CurrencyDtoMapper {

    /*fun mapResponseToDomainModel(response: Response<Currency>): Currencies {
        return Currencies(
            // смапить данные ответа в модель
        )
    }*/
}