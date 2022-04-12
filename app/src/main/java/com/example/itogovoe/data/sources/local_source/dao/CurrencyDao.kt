package com.example.itogovoe.data.sources.local_source.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesUiEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity
import com.example.itogovoe.data.sources.local_source.entities.InfoEntity

@Dao
interface CurrencyDao {

    // HistoryEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistoryItem(history: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity ORDER BY id DESC")
    fun readAllHistory(): LiveData<List<HistoryEntity>>

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


    // CurrenciesUiEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyUiItem(currencyUi: CurrenciesUiEntity)

    @Query("SELECT * FROM CurrenciesUiEntity")
    fun readAllCurrenciesUi(): LiveData<List<CurrenciesUiEntity>>

    @Query("DELETE FROM CurrenciesUiEntity")
    fun deleteAllCurrenciesUi()

    @Query("DELETE FROM CurrenciesUiEntity WHERE name = :name")
    fun deleteCurrencyUiItem(name: String)
}