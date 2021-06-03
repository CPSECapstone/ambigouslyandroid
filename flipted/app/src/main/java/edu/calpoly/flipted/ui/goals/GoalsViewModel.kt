package edu.calpoly.flipted.ui.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.businesslogic.goals.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GoalsViewModel(repo: GoalsRepo) : ViewModel() {
    private val _goals : MutableLiveData<List<Goal>> = MutableLiveData()
    val goals : LiveData<List<Goal>>
        get() = _goals

    private val getAllGoals = GetAllGoals(repo)
    private val editGoal = EditGoal(repo)

    init {
        viewModelScope.launch {
            while(true) {
                delay(5000)
                fetchGoals()
            }
        }
    }

    fun fetchGoals() {
        viewModelScope.launch {
            _goals.value = getAllGoals.execute()
        }
    }

    fun setGoalCompleted(goal: Goal, isComplete: Boolean) {
        viewModelScope.launch {
            val updatedGoal = UpdateGoalCompleted.execute(goal, isComplete)
            // We think we just marked the goal as complete.
            // However, something may have gone wrong when marking the goal as complete.
            // To prevent the server and client from getting out-of-sync, we'll refresh the
            // goals from the backend.
            // But that might take a while, so we update our local copy temporarily. That way
            // it'll display correctly while we wait for the backend response
            _goals.value = goals.value!!.map {
                if(it.uid == updatedGoal.uid)
                    updatedGoal
                else
                    it
            }
            editGoal.execute(updatedGoal)
            _goals.value = getAllGoals.execute()
        }
    }

    fun setSubgoalCompleted(goal: Goal, subgoal: SubGoal, isComplete: Boolean) {
        viewModelScope.launch {
            val updatedGoal = UpdateSubgoalCompleted.execute(goal, subgoal, isComplete)
            _goals.value = goals.value!!.map {
                if(it.uid == updatedGoal.uid)
                    updatedGoal
                else
                    it
            }
            editGoal.execute(updatedGoal)
            _goals.value = getAllGoals.execute()
        }
    }
}
