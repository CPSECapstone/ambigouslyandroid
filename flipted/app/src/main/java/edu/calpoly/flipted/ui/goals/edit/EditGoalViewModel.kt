package edu.calpoly.flipted.ui.goals.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.calpoly.flipted.backend.MockGoalsRepo
import edu.calpoly.flipted.businesslogic.goals.EditGoal
import edu.calpoly.flipted.businesslogic.goals.GetGoalById
import edu.calpoly.flipted.businesslogic.goals.Goal

class EditGoalViewModel : ViewModel() {
    val goal = MutableLiveData<Goal>()

    private val repo = MockGoalsRepo()
    private val getGoal = GetGoalById(repo)
    private val saveGoal = EditGoal(repo)
}