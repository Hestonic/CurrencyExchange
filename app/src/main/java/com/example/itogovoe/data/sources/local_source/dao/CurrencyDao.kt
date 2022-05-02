package com.example.itogovoe.data.sources.local_source.dao

import androidx.room.*
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyItem(currencies: CurrenciesEntity)

    @Query("SELECT * FROM CurrenciesEntity WHERE name LIKE :searchQuery")
    fun searchCurrenciesDatabase(searchQuery: String): List<CurrenciesEntity>

    @Query("SELECT * FROM CurrenciesEntity")
    fun readAllCurrencies(): List<CurrenciesEntity>

    @Update
    suspend fun updateCurrency(currency: CurrenciesEntity)

    @Query("SELECT * FROM CurrenciesEntity WHERE name = :name")
    fun readCurrency(name: String): CurrenciesEntity

}