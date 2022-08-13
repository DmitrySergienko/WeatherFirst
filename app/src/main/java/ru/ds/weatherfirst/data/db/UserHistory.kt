package ru.ds.weatherfirst.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_city")
data class UserHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val age: Int

)
