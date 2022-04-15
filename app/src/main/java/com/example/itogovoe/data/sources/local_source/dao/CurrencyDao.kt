package com.example.itogovoe.data.sources.local_source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    // HistoryEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistoryItem(history: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity ORDER BY date DESC")
    fun readAllHistory(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE date BETWEEN :dateTo AND :dateFrom ORDER BY date DESC")
    fun searchDateHistory(dateTo: Long, dateFrom: Long): LiveData<List<HistoryEntity>>

    @Query("DELETE FROM HistoryEntity")
    fun deleteAllHistory()

    /*@Query("SELECT * FROM HistoryEntity " +
            "WHERE (date >= :dateFrom AND date <= :dateTo) " +
            "OR currencyNameChild LIKE :searchQuery OR currencyNameParent LIKE :searchQuery")
    fun searchDateHistory(dateTo: Long, dateFrom: Long, searchQuery: String): List<HistoryEntity>*/


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


    /*// CurrenciesUiEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCurrencyUiItem(currencyDaoEntity: CurrenciesDaoEntity)

    @Update
    suspend fun updateCurrencyUiItem(currencyDaoEntity: CurrenciesDaoEntity)

    @Query("SELECT * FROM CurrenciesDaoEntity")
    fun readAllCurrenciesUi(): List<CurrenciesDaoEntity>

    @Query("DELETE FROM CurrenciesDaoEntity")
    fun deleteAllCurrenciesUi()

    @Query("DELETE FROM CurrenciesDaoEntity WHERE name = :name")
    fun deleteCurrencyUiItem(name: String)*/
}