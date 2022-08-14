package ru.ds.weatherfirst.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.ds.weatherfirst.data.db.databaseSan.TestDB


@Dao
interface HistoryDao {
    @Query("SELECT * FROM new_db")
    fun readAll(): Flow<List<TestDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: TestDB)


}