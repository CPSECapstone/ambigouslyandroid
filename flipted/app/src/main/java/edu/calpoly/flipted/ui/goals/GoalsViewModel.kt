package edu.calpoly.flipted.ui.goals


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloGoalsRepo
import edu.calpoly.flipted.backend.MockGoalsRepo
import edu.calpoly.flipted.businesslogic.goals.*
import kotlinx.coroutines.launch

class GoalsViewModel : ViewModel() {
    private val _goals : MutableLiveData<List<Goal>> = MutableLiveData()
    val goals : LiveData<List<Goal>>
        get() = _goals

    private val repo = ApolloGoalsRepo()
    private val getAllGoals = GetAllGoals(repo)
    private val updateGoalCompleted = UpdateGoalCompleted(repo)
    private val updateSubgoalCompleted = UpdateSubgoalCompleted(repo)

    fun fetchGoals() {
        viewModelScope.launch {
            _goals.value = getAllGoals.execute()
        }
    }

    fun setGoalCompleted(goal: Goal, isComplete: Boolean) {
        viewModelScope.launch {
            val updatedGoal = updateGoalCompleted.execute(goal, isComplete)
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

            _goals.value = getAllGoals.execute()
        }
    }

    fun setSubgoalCompleted(goal: Goal, subgoal: SubGoal, isComplete: Boolean) {
        viewModelScope.launch {
            val updatedGoal = updateSubgoalCompleted.execute(goal, subgoal, isComplete)
            _goals.value = goals.value!!.map {
                if(it.uid == updatedGoal.uid)
                    updatedGoal
                else
                    it
            }
            _goals.value = getAllGoals.execute()
        }
    }
}
