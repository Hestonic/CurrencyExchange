package com.example.itogovoe.data.sources.local_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.dao.HistoryDao
import com.example.itogovoe.data.sources.local_source.entities.CurrenciesEntity
import com.example.itogovoe.data.sources.local_source.entities.HistoryEntity

@Database(
    entities = [HistoryEntity::class, CurrenciesEntity::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract val currencyDao: CurrencyDao
    abstract val historyDao: HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null
        private const val DATABASE_NAME = "currency_database"

        fun getDatabase(context: Context): CurrencyDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}