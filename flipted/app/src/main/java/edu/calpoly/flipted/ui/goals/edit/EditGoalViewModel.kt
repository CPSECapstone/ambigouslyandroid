package edu.calpoly.flipted.ui.goals.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.calpoly.flipted.backend.MockGoalsRepo
import edu.calpoly.flipted.businesslogic.goals.*
import kotlinx.coroutines.launch
import java.util.*

class EditGoalViewModel : ViewModel() {
    private val _goal = MutableLiveData<UnsavedNewGoal>()

    private val repo = MockGoalsRepo()
    private val getGoal = GetGoalById(repo)
    private val editGoal = EditGoal(repo)
    private val saveNewGoal = SaveNewGoal(repo)

    var currSubgoalList : List<MutableSubGoal> = listOf()

    val goal: LiveData<UnsavedNewGoal>
        get() = _goal

    fun fetchGoal(goalId: String) {
        viewModelScope.launch {
            _goal.value = getGoal.execute(goalId)
        }
    }

    fun fillInEmptyGoal() {
        _goal.value = UnsavedNewGoal("", Date(), listOf(), "", false, true, null)
    }

    fun saveGoal() {
        val saveGoal = _goal.value?.let {
            when(it) {
                // FIXME: Any new subgoals added to an existing goal are deleted by the mapNotNull
                //        This is a symptom of the fact that the goals api does not currently
                //        currently support adding new subgoals to an existing goal
                is Goal -> Goal(it.title, it.uid, it.dueDate, it.completedDate, currSubgoalList.mapNotNull { sg -> sg.asSubGoal }, it.completed, it.category, it.favorited, it.ownedByStudent, it.pointValue)
                else -> UnsavedNewGoal(it.title, it.dueDate, currSubgoalList.mapNotNull { sg -> sg.asUnsavedNewSubGoal }, it.category, it.favorited, it.ownedByStudent, it.pointValue)
            }
        } ?: throw IllegalStateException("Attempt to save null goal")
        viewModelScope.launch {
            _goal.value = when(saveGoal) {
                is Goal -> editGoal.execute(saveGoal)
                else -> saveNewGoal.execute(saveGoal)
            }
        }

    }

    fun setGoalDueDate(date: Date) {
        _goal.value?.let {
            _goal.value = when(it) {
                is Goal -> Goal(it.title, it.uid, date, it.completedDate, it.subGoals, it.completed, it.category, it.favorited, it.ownedByStudent, it.pointValue)
                else -> UnsavedNewGoal(it.title, date, it.subGoals, it.category, it.favorited, it.ownedByStudent, it.pointValue)
            }
        } ?: throw IllegalStateException("Attempt to set date of null goal")
    }

    fun setGoalTitleText(title: String) {
        goal.value?.let {
            _goal.value = when(it) {
                is Goal -> Goal(title, it.uid, it.dueDate, it.completedDate, it.subGoals, it.completed, it.category, it.favorited, it.ownedByStudent, it.pointValue)
                else -> UnsavedNewGoal(title, it.dueDate, it.subGoals, it.category, it.favorited, it.ownedByStudent, it.pointValue)
            }
        } ?: throw IllegalStateException("Attempt to set title of null goal")
    }

    fun setGoalCategory(category: String) {
        _goal.value?.let {
            _goal.value = when(it) {
                is Goal -> Goal(it.title, it.uid, it.dueDate, it.completedDate, it.subGoals, it.completed, category, it.favorited, it.ownedByStudent, it.pointValue)
                else -> UnsavedNewGoal(it.title, it.dueDate, it.subGoals, category, it.favorited, it.ownedByStudent, it.pointValue)
            }
        } ?: throw java.lang.IllegalStateException("Attempt to set category of null goal")
    }

    fun validateGoal(): List<String> {
        return _goal.value?.let {
            ValidateGoals.execute(it)
        } ?: listOf("There is no goal yet")
    }

}