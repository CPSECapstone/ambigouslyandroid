package edu.calpoly.flipted.ui.myProgress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TargetsViewModel : ViewModel() {
    private val _targetList : MutableLiveData<List<String>> = MutableLiveData()
    private val _selectedTargetList : MutableLiveData<List<String>> = MutableLiveData()
    private val _errorMessage : MutableLiveData<String> = MutableLiveData()
}