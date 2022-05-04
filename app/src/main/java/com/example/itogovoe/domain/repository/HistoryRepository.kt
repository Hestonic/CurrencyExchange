package com.example.itogovoe.domain.repository

import com.example.itogovoe.domain.model.HistoryDtoModel
import java.time.LocalDateTime

interface HistoryRepository {

    suspend fun addHistoryItem(historyDomainModel: HistoryDtoModel)

    fun getHistory(): List<HistoryDtoModel>

    fun searchDateHistory(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<HistoryDtoModel>
}