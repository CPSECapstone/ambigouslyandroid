package edu.calpoly.flipted.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.ui.goals.GoalsViewModel
import edu.calpoly.flipted.ui.goals.edit.EditGoalViewModel
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.myProgress.missionDetails.MissionTaskViewModel
import edu.calpoly.flipted.ui.myProgress.missions.MissionsViewModel
import edu.calpoly.flipted.ui.myProgress.targets.TargetsViewModel
import edu.calpoly.flipted.ui.tasks.TaskViewModel

class DIViewModelFactory : ViewModelProvider.Factory {
    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
        = when (modelClass) {
            GoalsViewModel::class.java -> GoalsViewModel()
            EditGoalViewModel::class.java -> EditGoalViewModel()
            LoginViewModel::class.java -> LoginViewModel()
            MissionTaskViewModel::class.java -> MissionTaskViewModel()
            MissionsViewModel::class.java -> MissionsViewModel()
            TargetsViewModel::class.java -> TargetsViewModel()
            TaskViewModel::class.java -> TaskViewModel()
            else -> modelClass.newInstance()
        } as T
}