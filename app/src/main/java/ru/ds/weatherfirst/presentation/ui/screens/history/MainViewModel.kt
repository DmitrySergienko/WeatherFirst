package ru.ds.weatherfirst.presentation.ui.screens.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.ds.weatherfirst.data.db.HistoryRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    historyRepository: HistoryRepository
): ViewModel(){

    val readAll = historyRepository.readAll

}