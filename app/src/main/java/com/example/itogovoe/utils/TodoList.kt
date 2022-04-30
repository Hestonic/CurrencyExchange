package com.example.itogovoe.utils

// TODO: CurrencyFragment должен сохраняться выбранный элемент при перевороте экрана

// TODO:
//  Упорядочить функции в repository, localDateSource, во viewModels
//  Сделать во вью моделях вызовы в из бд через .let{}
//  Сделать во вью моделях get() для public LiveData
//  Сделать UiModel для ExchangeFragment
//  усовершенствовать HistoryUiModel

// TODO: HomeFragment
//  3 должен переживать поворот экрана
//  6 Сделать прогресс бар когда валюты подгружаются
//   - это можно сделать при помощи ViewModel + добавить в CurrencyUiModel поле isFavourite: Boolean

// TODO: FilterFragment
//  - Переделать FilterFragment под sealed class
//  - Перенести всю бизнес логику из FilterFragment во ViewModel
//  1. сделать фильтр по валютам
//  - По нажатию на одну из валют в фильтере будет отображаться только та валюта, где она есть (можно выбрать несколько)
//  - При повторном нажатии выбор данной валюты отменяется (либо сделать кнопку сброса фильтра по валютам)
//  - На экране с историей будет видно, что применён фильтр по валютам

// TODO: - AnalyticFragment
// TODO: ???????????????????