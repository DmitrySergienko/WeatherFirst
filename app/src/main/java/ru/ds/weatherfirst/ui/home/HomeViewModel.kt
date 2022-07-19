package ru.ds.weatherfirst.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.data.api.model.Hour
import ru.ds.weatherfirst.data.repository.WeatherRepo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo): ViewModel() {

        private val _state = MutableStateFlow(emptyList<Hour>())
        val state: StateFlow<List<Hour>>
        get() = _state



    init {
        viewModelScope.launch {
            val weather = weatherRepo.weatherResponse()
            _state.value = weather.forecast.forecastday[0].hour
        }
    }
}