package ru.ds.weatherfirst.ui.screens.days

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.data.api.model.Forecastday
import ru.ds.weatherfirst.data.repository.WeatherRepo
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
):ViewModel(){

    private val _stateDay = MutableStateFlow(emptyList<Forecastday>())
    val stateDay :StateFlow<List<Forecastday>>
    get() = _stateDay

    init {
        viewModelScope.launch {
            val day = weatherRepo.weatherResponse()
            _stateDay.value = day.forecast.forecastday
            //val test = day.forecast.forecastday
            //Log.d("VVV","${test.size}")
        }
    }
}