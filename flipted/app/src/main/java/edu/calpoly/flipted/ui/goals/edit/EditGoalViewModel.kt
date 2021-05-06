package edu.calpoly.flipted.ui.goals.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.ApolloGoalsRepo
import edu.calpoly.flipted.backend.MockGoalsRepo
import edu.calpoly.flipted.businesslogic.goals.*
import kotlinx.coroutines.launch
import java.util.*

class EditGoalViewModel : ViewModel() {
    private val _goal = MutableLiveData<Goal>()

    private val repo = ApolloGoalsRepo()
    private val getGoal = GetGoalById(repo)
    private val editGoal = EditGoal(repo)

    var currSubgoalList : List<MutableSubGoal> = listOf()

    val goal: LiveData<Goal>
        get() = _goal

    fun fetchGoal(goalId: String) {
        viewModelScope.launch {
            _goal.value = getGoal.execute(goalId)
        }
    }

    fun fillInEmptyGoal() {
        _goal.value = Goal(null, "", Date(), false, null, listOf(), "", false, true, null)
    }

    fun saveGoal() {
        val saveGoal = _goal.value?.let {
            Goal(it.uid, it.title, it.dueDate, it.completed, it.completedDate, currSubgoalList.map{sg -> sg.asSubGoal}, it.category, it.favorited, it.isOwnedByStudent, it.pointValue)
        } ?: throw IllegalStateException("Attempt to save null goal")
        viewModelScope.launch {
            editGoal.execute(saveGoal)
        }

    }

    fun setGoalDueDate(date: Date) {
        _goal.value?.let {
            _goal.value = Goal(it.uid, it.title, date, it.completed, it.completedDate, it.subGoals, it.category, it.favorited, it.isOwnedByStudent, it.pointValue)
        }
    }

    fun setGoalTitleText(title: String) {
        goal.value?.let {
            _goal.value = Goal(it.uid, title, it.dueDate, it.completed, it.completedDate, it.subGoals, it.category, it.favorited, it.isOwnedByStudent, it.pointValue)
        }
    }

    fun setGoalCategory(category: String) {
        _goal.value?.let {
            _goal.value = Goal(it.uid, it.title, it.dueDate, it.completed, it.completedDate, it.subGoals, category, it.favorited, it.isOwnedByStudent, it.pointValue)
        }
    }

    fun validateGoal(): List<String> {
        return _goal.value?.let {
            ValidateGoals.execute(it)
        } ?: listOf("There is no goal yet")
    }

}