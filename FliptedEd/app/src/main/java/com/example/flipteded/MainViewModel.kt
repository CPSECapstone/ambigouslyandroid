package com.example.flipteded

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _data : MutableLiveData<List<String>> = MutableLiveData()
    val data : LiveData<List<String>>
        get() = _data

    fun refresh() {
        viewModelScope.launch {
            _data.value = VerticalSliceNetworkCalls.getStringsFromAWS(getApplication())
        }
    }

    fun save(str : String) {
        Log.d("MainViewModel", "Saving string $str");
        viewModelScope.launch {
            VerticalSliceNetworkCalls.sendStringToAWS(getApplication(), str)
        }
    }
}