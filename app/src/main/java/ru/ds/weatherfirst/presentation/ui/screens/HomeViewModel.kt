package ru.ds.weatherfirst.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.data.repository.WeatherRepo
import ru.ds.weatherfirst.domain.location.LocationTracker
import ru.ds.weatherfirst.domain.model.Condition
import ru.ds.weatherfirst.domain.model.Current
import ru.ds.weatherfirst.domain.model.Forecastday
import ru.ds.weatherfirst.domain.model.Hour
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val locationTracker: LocationTracker
) : ViewModel() {

    //for hours
    private val _state = MutableStateFlow(emptyList<Hour>())
    val state: StateFlow<List<Hour>>
        get() = _state

    //for current
    private val _stateMain =
        MutableStateFlow(Current(0, Condition("", "_"), 0.00, 0, 0, "", 0.1, 0.0, 0.00))
    val stateMain: StateFlow<Current>
        get() = _stateMain

    //for days
    private val _stateDay = MutableStateFlow(emptyList<Forecastday>())
    val stateDay: StateFlow<List<Forecastday>>
        get() = _stateDay


    fun getWeather(city: String) {
        viewModelScope.launch {
            val lat = locationTracker.getCurrentLocation()?.latitude.toString()
            val lon = locationTracker.getCurrentLocation()?.longitude.toString()
            if (city == "default") {
            val day = weatherRepo.weatherResponse("$lat,$lon")
                //for dates
                _stateDay.value = day.forecast.forecastday

                //for hours and current
                val weather = weatherRepo.weatherResponse("$lat,$lon")
                _state.value = weather.forecast.forecastday[0].hour

                _stateMain.value = weather.current
            } else {
                val day = weatherRepo.weatherResponse(city)
                _stateDay.value = day.forecast.forecastday
                val weather = weatherRepo.weatherResponse(city)
                _state.value = weather.forecast.forecastday[0].hour
                _stateMain.value = weather.current
            }
        }
    }
}

