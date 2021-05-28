package edu.calpoly.flipted.ui

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.kotlin.core.Amplify
import edu.calpoly.flipted.R
import edu.calpoly.flipted.backend.*
import edu.calpoly.flipted.ui.goals.GoalsViewModel
import edu.calpoly.flipted.ui.goals.edit.EditGoalViewModel
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.missions.MissionFragment
import edu.calpoly.flipted.ui.myProgress.missionDetails.MissionTaskViewModel
import edu.calpoly.flipted.ui.myProgress.targets.TargetsViewModel
import edu.calpoly.flipted.ui.tasks.ReviewResultsFragment
import edu.calpoly.flipted.ui.tasks.TaskFragment
import edu.calpoly.flipted.viewmodels.MissionsViewModel
import edu.calpoly.flipted.viewmodels.NavViewModel
import edu.calpoly.flipted.viewmodels.TaskViewModel

class DIViewModelFactory : ViewModelProvider.Factory {
    class AmplifyAuthProvider : AuthProvider {
        override suspend fun getToken(): String {
            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession

            val id = session.userPoolTokens

            if (id.type == AuthSessionResult.Type.FAILURE) {
                Log.i("AuthQuickStart", "IdentityId not present: ${id.error}")
                throw IllegalStateException();
            }

            val key = id.value!!.accessToken
            Log.i("tag", key)

            return key
        }

    }

    class MainNavigator : NavViewModel.Navigator {
        override fun openTaskFragment(fragmentManager: FragmentManager, taskUid: String) {
            fragmentManager.commit {
                replace(R.id.main_view, TaskFragment.newInstance(taskUid))
                addToBackStack("Start task")
                setReorderingAllowed(true)
            }
        }

        override fun openTaskResultsFragment(fragmentManager: FragmentManager) {
            fragmentManager.commit {
                replace(R.id.main_view, ReviewResultsFragment.newInstance())
                addToBackStack("Start task")
                setReorderingAllowed(true)
            }
        }

        override fun openMissionFragment(fragmentManager: FragmentManager, missionId: String) {
            fragmentManager.commit {
                replace(R.id.main_view, MissionFragment.newInstance(missionId))
                addToBackStack(null)
                setReorderingAllowed(true)
            }
        }

    }

    companion object {
        private val authProvider by lazy { AmplifyAuthProvider() }
        private val goalsRepo by lazy { ApolloGoalsRepo(authProvider) }
        private val missionsRepo by lazy { ApolloMissionsRepo(authProvider) }
        private val learningTargetsRepo by lazy { ApolloLearningTargetsRepo(authProvider) }
        private val tasksRepo by lazy { ApolloTasksRepo(authProvider) }
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
            NavViewModel::class.java -> NavViewModel(MainNavigator())
            else -> modelClass.newInstance()
        } as T
}