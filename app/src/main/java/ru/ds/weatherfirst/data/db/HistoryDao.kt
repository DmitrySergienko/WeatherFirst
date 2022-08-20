package ru.ds.weatherfirst.data.db


import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Query("SELECT * FROM new_db")
    fun readAll(): Flow<List<TestDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(history: TestDB)

    @Delete
    fun deleteItem(history: TestDB)

    @Query("DELETE FROM new_db")
    fun deleteAll()

    @Query("SELECT * FROM new_db where id = :id")
    fun getByID(id:Int): TestDB

    @Query("select * from new_db where name = :name")
    fun getByName(name:String): TestDB




}