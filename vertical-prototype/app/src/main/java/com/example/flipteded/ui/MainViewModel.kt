package com.example.flipteded.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.flipteded.backend.ApolloGoalsRepo
import com.example.flipteded.businesslogic.goals.GetAllGoals
import com.example.flipteded.businesslogic.goals.Goal
import com.example.flipteded.businesslogic.goals.GoalCompletion
import com.example.flipteded.businesslogic.goals.SaveNewCompletion
import com.example.flipteded.businesslogic.old.DataEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _goals : MutableLiveData<List<Goal>> = MutableLiveData()
    val goals : LiveData<List<Goal>>
        get() = _goals

    private val repo = ApolloGoalsRepo()
    private val getAllGoals = GetAllGoals(repo)
    private val saveCompletion = SaveNewCompletion(repo)

    val data : LiveData<List<String>> = MutableLiveData<List<String>>()

    fun reload() {
        viewModelScope.launch {
            _goals.value = getAllGoals.execute()
        }
    }

    fun save(completion : GoalCompletion) {
        viewModelScope.launch {
            val updatedGoal = saveCompletion.execute(completion)
            if(updatedGoal == null) {
                Log.e("MainViewModel", "Error saving updated goal!")
                //TODO: We need better error handling
                return@launch
            }
            if(_goals.value == null)
                reload()
            val idx = _goals.value!!.indexOfFirst{it.uid == updatedGoal.uid}
            if(idx == -1) {
                Log.e("MainViewModel", "Saved completion to unknown goal!")
                _goals.value = _goals.value!! + updatedGoal
            } else {
                _goals.value = _goals.value!!.map {
                    if(it.uid == updatedGoal.uid) updatedGoal else it
                }
            }
        }
    }
}