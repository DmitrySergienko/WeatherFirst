package ru.ds.weatherfirst.presentation.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.ds.weatherfirst.data.repository.WeatherRepo
import ru.ds.weatherfirst.domain.location.LocationTracker
import ru.ds.weatherfirst.domain.model.Current
import ru.ds.weatherfirst.domain.model.Forecastday
import ru.ds.weatherfirst.domain.model.Hour
import ru.ds.weatherfirst.domain.model.Location
import ru.ds.weatherfirst.presentation.utils.WeatherState
import java.io.IOException
import javax.inject.Inject

const val MY_TAG = "VVV"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var weatherState: WeatherState by mutableStateOf(WeatherState.Loading)
        private set

    //hours
    private val _state = MutableStateFlow(emptyList<Hour>())
    val state: StateFlow<List<Hour>>
        get() = _state

    //location
    private val _location = MutableStateFlow(Location())
    val location: StateFlow<Location>
        get() = _location

    //current
    private val _stateMain = MutableStateFlow(Current())
    val stateMain: StateFlow<Current>
        get() = _stateMain

    //days
    private val _stateDay = MutableStateFlow(emptyList<Forecastday>())
    val stateDay: StateFlow<List<Forecastday>>
        get() = _stateDay

    fun getWeather(city: String) {
        viewModelScope.launch {
            if (city == "default") {
                weatherState = try {
                    val lat = locationTracker.getCurrentLocation()?.latitude.toString()
                    val lon = locationTracker.getCurrentLocation()?.longitude.toString()
                    Log.d(MY_TAG, "lat: $lat")
                    Log.d(MY_TAG, "lon: $lon")
                    val data = weatherRepo.weatherResponse("$lat,$lon")
                    Log.d(MY_TAG, "getWeather() called: $weatherState")
                    Log.d(MY_TAG, "data: $data")
                    //for dates
                    _stateDay.value = data.forecast.forecastday
                    //for hours and current
                    _state.value = data.forecast.forecastday[0].hour
                    //for location
                    _location.value = data.location
                    _stateMain.value = data.current
                    WeatherState.Success(data)

                    //IOException
                } catch (e: IOException) {
                    Log.e(MY_TAG, "getWeather() Error: $weatherState")
                    WeatherState.Error
                    //HttpException
                } catch (e: HttpException) {
                    WeatherState.Error
                }
            } else {
                weatherState = try {
                    val data = weatherRepo.weatherResponse(city)
                    _stateDay.value = data.forecast.forecastday
                    _state.value = data.forecast.forecastday[0].hour
                    _stateMain.value = data.current

                    WeatherState.Success(data)
                    //IOException
                } catch (e: IOException) {
                    WeatherState.Error
                    //HttpException
                } catch (e: HttpException) {
                    WeatherState.Error
                }
            }
        }
    }

    var remove_item = ""
        private set // можем поменять только из viewModel

    fun passItem(newItem: String) {
        remove_item = newItem
    }

    init {
        getWeather("default")
    }

}


