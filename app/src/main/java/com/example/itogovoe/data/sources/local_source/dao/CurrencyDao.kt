package com.example.itogovoe.data.sources.local_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity

@Dao
interface CurrencyDao {

    // HistoryEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistoryItem(history: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity ORDER BY date DESC")
    fun readAllHistory(): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    fun deleteAllHistory()

    @Query("SELECT * FROM HistoryEntity WHERE date BETWEEN :dateFrom AND :dateTo ORDER BY date DESC")
    fun searchDateHistory(dateFrom: Long, dateTo: Long): List<HistoryEntity>


    // InfoEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInfo(info: InfoEntity)

    @Query("SELECT * FROM InfoEntity")
    fun readAllInfo(): InfoEntity

    @Query("DELETE FROM InfoEntity")
    fun deleteAllInfo()


    // CurrenciesEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyItem(currencies: CurrenciesEntity)

    @Query("SELECT * FROM CurrenciesEntity")
    fun readAllCurrencies(): List<CurrenciesEntity>

    @Query("DELETE FROM CurrenciesEntity")
    fun deleteAllCurrencies()
}