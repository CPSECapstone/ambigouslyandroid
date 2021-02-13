package com.example.flipteded

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val REFRESH_PERIOD : Long = 5000;
    }
    private val _data : MutableLiveData<List<String>> = MutableLiveData()
    val data : LiveData<List<String>>
        get() = _data

    init {
        viewModelScope.launch {
            _data.value = VerticalSliceNetworkCalls.getStringsFromAWS(getApplication())
            delay(REFRESH_PERIOD)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _data.value = VerticalSliceNetworkCalls.getStringsFromAWS(getApplication())
        }
    }

    fun save(str : String) {
        viewModelScope.launch {
            VerticalSliceNetworkCalls.sendStringToAWS(getApplication(), str)
        }
    }
}