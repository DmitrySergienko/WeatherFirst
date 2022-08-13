package ru.ds.weatherfirst.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserHistory::class], version = 1, exportSchema = true)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}