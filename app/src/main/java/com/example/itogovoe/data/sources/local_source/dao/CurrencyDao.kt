package com.example.itogovoe.data.sources.local_source.dao

import androidx.room.*
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity

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


    // CurrenciesEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyItem(currencies: CurrenciesEntity)

    @Query("SELECT * FROM CurrenciesEntity")
    fun readAllCurrencies(): List<CurrenciesEntity>

    @Update
    suspend fun updateCurrency(currency: CurrenciesEntity)

    @Query("SELECT * FROM CurrenciesEntity WHERE name = :name")
    fun readCurrency(name: String): CurrenciesEntity

    @Query("DELETE FROM CurrenciesEntity")
    fun deleteAllCurrencies()
}