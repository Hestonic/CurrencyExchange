package com.example.itogovoe.data.sources.local_source.database

import android.content.Context
import androidx.room.*
import com.example.itogovoe.data.sources.local_source.converters.DateConverter
import com.example.itogovoe.data.sources.local_source.dao.CurrencyDao
import com.example.itogovoe.data.sources.local_source.entities.*

@Database(
    entities = [HistoryEntity::class, InfoEntity::class, CurrenciesEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract val currencyDao: CurrencyDao

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