package edu.calpoly.flipted.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.kotlin.core.Amplify
import edu.calpoly.flipted.backend.*
import edu.calpoly.flipted.ui.goals.GoalsViewModel
import edu.calpoly.flipted.ui.goals.edit.EditGoalViewModel
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.myProgress.missionDetails.MissionTaskViewModel
import edu.calpoly.flipted.ui.myProgress.missions.MissionsViewModel
import edu.calpoly.flipted.ui.myProgress.targets.TargetsViewModel
import edu.calpoly.flipted.ui.tasks.TaskViewModel

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
            else -> modelClass.newInstance()
        } as T
}