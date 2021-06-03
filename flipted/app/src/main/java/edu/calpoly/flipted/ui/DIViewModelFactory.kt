package edu.calpoly.flipted.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.backend.ApolloGoalsRepo
import edu.calpoly.flipted.backend.ApolloLearningTargetsRepo
import edu.calpoly.flipted.backend.ApolloMissionsRepo
import edu.calpoly.flipted.backend.ApolloTasksRepo
import edu.calpoly.flipted.ui.goals.GoalsViewModel
import edu.calpoly.flipted.ui.goals.edit.EditGoalViewModel
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.myProgress.missionDetails.MissionTaskViewModel
import edu.calpoly.flipted.ui.myProgress.missions.MissionsViewModel
import edu.calpoly.flipted.ui.myProgress.targets.TargetsViewModel
import edu.calpoly.flipted.ui.tasks.TaskViewModel

class DIViewModelFactory : ViewModelProvider.Factory {
    companion object {
        private val goalsRepo by lazy { ApolloGoalsRepo() }
        private val missionsRepo by lazy { ApolloMissionsRepo() }
        private val learningTargetsRepo by lazy { ApolloLearningTargetsRepo() }
        private val tasksRepo by lazy { ApolloTasksRepo() }
    }

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
            GoalsViewModel::class.java -> GoalsViewModel(goalsRepo)
            EditGoalViewModel::class.java -> EditGoalViewModel(goalsRepo)
            LoginViewModel::class.java -> LoginViewModel()
            MissionTaskViewModel::class.java -> MissionTaskViewModel(missionsRepo)
            MissionsViewModel::class.java -> MissionsViewModel(missionsRepo, tasksRepo)
            TargetsViewModel::class.java -> TargetsViewModel(learningTargetsRepo)
            TaskViewModel::class.java -> TaskViewModel(tasksRepo)
            else -> modelClass.newInstance()
        } as T
}