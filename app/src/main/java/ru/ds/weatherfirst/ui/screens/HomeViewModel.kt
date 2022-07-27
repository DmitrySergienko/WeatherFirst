package ru.ds.weatherfirst.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.data.api.model.Condition
import ru.ds.weatherfirst.data.api.model.Current
import ru.ds.weatherfirst.data.api.model.Forecastday
import ru.ds.weatherfirst.data.api.model.Hour
import ru.ds.weatherfirst.data.repository.WeatherRepo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    //for hours
    private val _state = MutableStateFlow(emptyList<Hour>())
    val state: StateFlow<List<Hour>>
        get() = _state

    //for current
    private val _stateMain = MutableStateFlow(
        Current
            (0, Condition("Any", ""), 0.00, 0, 0, "", 0.0, 0.0, 0.00)
    )
    val stateMain: StateFlow<Current>
        get() = _stateMain

    //for days
    private val _stateDay = MutableStateFlow(emptyList<Forecastday>())
    val stateDay: StateFlow<List<Forecastday>>
        get() = _stateDay


    fun getWeather(city: String) {
        viewModelScope.launch {
            val weather = weatherRepo.weatherResponse(city)
            //for hours
            _state.value = weather.forecast.forecastday[0].hour
            //for current
            _stateMain.value = weather.current
            //for dates
            val day = weatherRepo.weatherResponse(city)
            _stateDay.value = day.forecast.forecastday

        }
    }
}