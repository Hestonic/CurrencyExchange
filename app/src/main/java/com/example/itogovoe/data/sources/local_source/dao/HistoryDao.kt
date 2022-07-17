package com.example.itogovoe.data.sources.local_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity

@Dao
interface HistoryDao {
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHistoryItem(history: HistoryEntity)
    
    @Query("SELECT * FROM HistoryEntity ORDER BY date DESC")
    fun readAllHistory(): List<HistoryEntity>
    
    @Query("SELECT * FROM HistoryEntity WHERE date BETWEEN :dateFrom AND :dateTo ORDER BY date DESC")
    fun searchDateHistory(dateFrom: Long, dateTo: Long): List<HistoryEntity>
    
    @Query(
        "SELECT * FROM HistoryEntity WHERE " +
          "currencyNameParent = :currency OR currencyNameChild = :currency ORDER BY date DESC"
    )
    fun searchCurrenciesHistory(currency: String): List<HistoryEntity>
    
}