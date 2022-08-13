package ru.ds.weatherfirst.data.db

import androidx.room.Dao
import androidx.room.Query


import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_city")
    fun readAll(): Flow<List<UserHistory>>
}