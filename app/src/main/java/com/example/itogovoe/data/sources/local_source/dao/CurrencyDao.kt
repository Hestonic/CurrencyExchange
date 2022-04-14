package com.example.itogovoe.data.sources.local_source.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.itogovoe.data.sources.local_source.entities.*

@Dao
interface CurrencyDao {

    // HistoryEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistoryItem(history: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity ORDER BY id DESC")
    fun readAllHistory(): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    fun deleteAllHistory()


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