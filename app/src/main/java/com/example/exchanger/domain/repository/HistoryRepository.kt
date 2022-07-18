package com.example.exchanger.domain.repository

import com.example.exchanger.domain.model.HistoryDtoModel
import java.time.LocalDateTime

interface HistoryRepository {
    suspend fun addHistoryItem(historyDomainModel: HistoryDtoModel)
    fun getHistory(): List<HistoryDtoModel>
    fun searchDateHistory(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<HistoryDtoModel>
    fun searchCurrenciesHistory(currency: String): List<HistoryDtoModel>
}