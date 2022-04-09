package com.example.itogovoe.utils

// TODO: 1. HomeFragment
// TODO: 1.1 При долгом нажатии на валюту, она перемещалась наверх и становилась зелёной
// TODO: 1.2 При повторном долгом нажатии на уже выбранный item он уходит обратно (становится не выбранным)
// TODO: 1.3 При повторном долгом нажатии на другую валюту переходим в ExchangeFragment и туда переносятся все данные
// TODO: 1.4 При нажатии на звёздочку валюта добавляется в избранное (Звёздочка заполняется) Реализовать логику добавления в избранное
// TODO: Пользователь видит валюты отсортированы по последним использованным
// это можно сделать при помощи ViewModel + добавить в CurrencyUiModel поле isFavourite: Boolean

// TODO: 2. ExchangeFragment
// TODO: 2.1 Реализовать логику конвертора
// TODO: 2.2 При нажатии Enter(на клаве) в полях данные не обнулялись
// TODO: 2.3 В поля можно вводить только цифры и точки

// TODO: 3. HistoryFragment
// TODO: 3.1 Сделать таблицу в БД для History
// TODO: 3.2 Реализовать кнопку сохранения в историю подсчёта валют
// TODO: 3.3 Выводить в HistoryFragment историю
// TODO: 3.4 Сделать кнопку отчистки истории

// TODO: 4. FilterFragment
// TODO: 4.1 берутся валюты из таблицы БД History и выводятся в ресайклер во фрагменте
// TODO: 4.1.1 По нажатию на одну из валют в истории будет отображаться только так валюта, где она есть (можно выбрать несколько)
// TODO: 4.2 По нажатию на кнопку "За всё время" и тд она закрашивается и к истории применяется данный фильтр
// TODO: 4.3 По повторному нажатию на кнопку "За всё время" фильтр отменяется
// TODO: 4.4 Сделать фильтры "За всё время", "За месяц", "За неделю" взаимо исключаемыми
// TODO: 4.5 Последняя дата это сегодняшняя дата, либо последний применённый фильтр
// TODO: 4.6 Выбор даты пользователем (нельзя первую дату сделать больше второй)
// TODO: 4.7 Сброс применённых фильтров пользователем

// TODO: Настроить LocalStore через RoomDB и сохранение последнего запроса из сети
// (в БД хранить: 1. таблица History; 2. Таблица последнего запроса валют)
// TODO: Реализовать алгоритм запроса свежих данных в Repository

// TODO: - AnalyticFragment
// TODO: ???????????????????


// фильтрация сделать sealed class
// TODO: посмотреть модификаторы доступа inturnal, private, inner, public почитать
// инкапсуляция, полиморфизм, ещё что то (три основные признаки ООП)