package edu.calpoly.flipted.ui.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockGoalsRepo
import edu.calpoly.flipted.businesslogic.goals.GetAllGoals
import edu.calpoly.flipted.businesslogic.goals.Goal
import kotlinx.coroutines.launch

class GoalsViewModel : ViewModel() {
    private val _goals : MutableLiveData<List<Goal>> = MutableLiveData()
    val goals : LiveData<List<Goal>>
        get() = _goals

    private val repo = MockGoalsRepo()
    private val getAllGoals = GetAllGoals(repo)

    fun fetchGoals() {
        viewModelScope.launch {
            _goals.value = getAllGoals.execute()
        }
    }
}