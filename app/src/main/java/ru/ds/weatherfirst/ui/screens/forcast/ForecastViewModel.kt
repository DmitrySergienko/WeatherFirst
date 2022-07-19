package ru.ds.weatherfirst.ui.screens.forcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.data.api.model.Forecast
import ru.ds.weatherfirst.data.api.model.Hour
import ru.ds.weatherfirst.data.api.model.Weather
import ru.ds.weatherfirst.data.repository.WeatherRepo
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
) : ViewModel() {

    private val _statef = MutableStateFlow(emptyList<Forecast>())
    val statef: StateFlow<List<Forecast>>
        get() = _statef

    init {
        viewModelScope.launch {
            val weather = weatherRepo.weatherResponse()
            //_statef.value = weather.forecast

        }
    }
}