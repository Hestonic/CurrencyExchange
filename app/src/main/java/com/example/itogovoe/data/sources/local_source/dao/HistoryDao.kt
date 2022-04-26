package com.example.itogovoe.data.sources.local_source.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity

// TODO:
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistoryItem(history: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity ORDER BY date DESC")
    fun readAllHistory(): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    fun deleteAllHistory()

    @Query("SELECT * FROM HistoryEntity WHERE date BETWEEN :dateFrom AND :dateTo ORDER BY date DESC")
    fun searchDateHistory(dateFrom: Long, dateTo: Long): List<HistoryEntity>

}