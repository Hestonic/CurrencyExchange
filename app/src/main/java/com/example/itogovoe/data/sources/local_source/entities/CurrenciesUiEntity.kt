package com.example.itogovoe.data.sources.local_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrenciesUiEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val position: Int,
    var isFavourite: Boolean
)
