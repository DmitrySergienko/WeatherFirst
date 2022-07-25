package ru.ds.weatherfirst.ui.screens.hours

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.data.api.model.Condition
import ru.ds.weatherfirst.data.api.model.Current
import ru.ds.weatherfirst.data.api.model.Hour
import ru.ds.weatherfirst.data.api.model.Weather
import ru.ds.weatherfirst.data.repository.WeatherRepo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<Hour>())
    val state: StateFlow<List<Hour>>
        get() = _state

    private val _stateMain = MutableStateFlow(Current
        (0, Condition("Any",""),0.00,0,0,"",0.0,0.0,0.00))
    val stateMain: StateFlow<Current>
        get() = _stateMain

    init {

        getWeather(city = "Dubai")

    }

    fun getWeather(city: String) {
        viewModelScope.launch {
            val weather = weatherRepo.weatherResponse(city)
            _state.value = weather.forecast.forecastday[0].hour
            val test = weather.forecast.forecastday[0].hour
            Log.d("VVV", "hours ${test.size}")


            _stateMain.value = weather.current
        }
    }
}