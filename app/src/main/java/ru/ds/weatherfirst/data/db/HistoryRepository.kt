package ru.ds.weatherfirst.data.db

import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {
    val readAll = historyDao.readAll()
}