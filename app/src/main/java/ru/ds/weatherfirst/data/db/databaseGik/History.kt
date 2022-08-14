package ru.ds.weatherfirst.data.db.databaseGik

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "history_name") val name: String,
    @ColumnInfo(name = "history_age")val age:Int
)
