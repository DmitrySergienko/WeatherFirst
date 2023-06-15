package ru.ds.weatherfirst.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TestDB::class], version = 2, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}