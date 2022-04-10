package com.example.itogovoe.data.source.local_source.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.itogovoe.data.source.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.source.local_source.entities.HistoryEntity
import com.example.itogovoe.data.source.local_source.entities.InfoEntity

@Dao
interface CurrencyDao {

    // HistoryEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistoryItem(history: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity ORDER BY id ASC")
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

    /*@Query("SELECT lastUploadDate FROM InfoEntity WHERE id = :key")
    fun readLastUploadInfoFromInfoEntity(key: Int): LiveData<InfoEntity>*/


    // CurrenciesEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyItem(currencies: CurrenciesEntity)

    @Query("SELECT * FROM CurrenciesEntity")
    fun readAllCurrencies(): List<CurrenciesEntity>

    @Query("DELETE FROM CurrenciesEntity")
    fun deleteAllCurrencies()


    // TODO: Не забыть удалить
    // Currencies
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalCurrencies(infoEntity: InfoEntity)

    @Transaction
    @Query("SELECT * FROM InfoEntity")
    fun getLocalCurrencies(): LiveData<List<InfoWithCurrencies>>

    @Query("DELETE FROM InfoEntity")
    fun deleteAllLocalCurrencies()*/
}